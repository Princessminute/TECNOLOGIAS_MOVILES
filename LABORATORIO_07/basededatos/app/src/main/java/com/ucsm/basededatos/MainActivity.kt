package com.ucsm.basededatos

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ucsm.basededatos.data.AppDatabase
import com.ucsm.basededatos.data.Articulo
import com.ucsm.basededatos.data.ArticuloRepository
import com.ucsm.basededatos.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: ArticuloRepository

    private var articuloActual: Articulo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = AppDatabase.getInstance(this).articuloDao()
        repository = ArticuloRepository(dao)

        Log.d("ACTIVITY", "Repository inicializado")

        binding.btnRegistrar.setOnClickListener { registrar() }
        binding.btnBuscar.setOnClickListener { buscar() }
        binding.btnModificar.setOnClickListener { modificar() }
        binding.btnEliminar.setOnClickListener { eliminar() }
        binding.btnListar.setOnClickListener { observarLista() }

        binding.txtResultado.text = "Presiona 'Ver artículos' para listar"
    }

    // ---------------- FLOW (LISTAR) ----------------
    private fun observarLista() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                Log.d("ACTIVITY", "Iniciando flujo de listado")
                repository.listarTodos().collect { lista ->
                    Log.d("ACTIVITY", "Lista recibida: ${lista.size} elementos")
                    mostrarLista(lista)
                }
            }
        }
    }

    private fun mostrarLista(lista: List<Articulo>) {
        val texto = StringBuilder()

        if (lista.isEmpty()) {
            texto.append("No hay artículos registrados")
        } else {
            lista.forEach {
                texto.append(
                    "Código: ${it.codigo}\n" +
                            "Descripción: ${it.descripcion}\n" +
                            "Precio: S/ ${it.precio}\n\n"
                )
            }
        }

        binding.txtResultado.text = texto.toString()
    }

    // ---------------- UTIL ----------------
    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun limpiarCampos() {
        binding.txtCodigo.setText("")
        binding.txtDescripcion.setText("")
        binding.txtPrecio.setText("")
    }

    // ---------------- REGISTRAR ----------------
    private fun registrar() {
        val codigo = binding.txtCodigo.text.toString()
        val descripcion = binding.txtDescripcion.text.toString()
        val precio = binding.txtPrecio.text.toString()

        if (codigo.isEmpty() || descripcion.isEmpty() || precio.isEmpty()) {
            toast("Complete todos los campos")
            return
        }

        val articulo = Articulo(
            codigo = codigo.toInt(),
            descripcion = descripcion,
            precio = precio.toDouble()
        )

        lifecycleScope.launch {
            try {
                Log.d("ACTIVITY", "Insertando artículo")
                repository.insertar(articulo)
                toast("Registro exitoso")
                limpiarCampos()
                articuloActual = null
            } catch (e: Exception) {
                Log.e("ACTIVITY", "Error al insertar", e)
                toast("Error: ${e.message}")
            }
        }
    }

    // ---------------- BUSCAR ----------------
    private fun buscar() {
        val codigo = binding.txtCodigo.text.toString()

        if (codigo.isEmpty()) {
            toast("Ingrese el código")
            return
        }

        lifecycleScope.launch {
            Log.d("ACTIVITY", "Buscando artículo")
            val articulo = repository.buscarPorCodigo(codigo.toInt())

            if (articulo != null) {
                articuloActual = articulo

                binding.txtDescripcion.setText(articulo.descripcion)
                binding.txtPrecio.setText(articulo.precio.toString())

                binding.txtResultado.text =
                    "Artículo encontrado\n\n" +
                            "Código: ${articulo.codigo}\n" +
                            "Descripción: ${articulo.descripcion}\n" +
                            "Precio: S/ ${articulo.precio}"

                toast("Artículo cargado")
            } else {
                articuloActual = null
                limpiarCampos()
                binding.txtResultado.text = "No se encontró el artículo"
            }
        }
    }

    // ---------------- MODIFICAR ----------------
    private fun modificar() {
        val base = articuloActual ?: run {
            toast("Primero busque un artículo")
            return
        }

        val descripcion = binding.txtDescripcion.text.toString()
        val precio = binding.txtPrecio.text.toString()

        if (descripcion.isEmpty() || precio.isEmpty()) {
            toast("Complete los campos")
            return
        }

        val actualizado = Articulo(
            codigo = base.codigo,
            descripcion = descripcion,
            precio = precio.toDouble()
        )

        lifecycleScope.launch {
            Log.d("ACTIVITY", "Actualizando artículo")
            val filas = repository.actualizar(actualizado)

            toast(if (filas == 1) "Actualizado" else "No se pudo actualizar")

            if (filas == 1) {
                limpiarCampos()
                articuloActual = null
            }
        }
    }

    // ---------------- ELIMINAR ----------------
    private fun eliminar() {
        val base = articuloActual ?: run {
            toast("Primero busque un artículo")
            return
        }

        lifecycleScope.launch {
            Log.d("ACTIVITY", "Eliminando artículo")
            val filas = repository.eliminarPorCodigo(base.codigo)

            toast(if (filas == 1) "Eliminado" else "No se pudo eliminar")

            if (filas == 1) {
                limpiarCampos()
                articuloActual = null
            }
        }
    }
}