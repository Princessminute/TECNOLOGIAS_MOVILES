package com.example.peluchesapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class DetalleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        // 🔹 Toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarDetalle)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 🔹 Recibir datos
        val nombre = intent.getStringExtra("nombre") ?: "Sin nombre"
        val precio = intent.getDoubleExtra("precio", 0.0)
        val cantidad = intent.getIntExtra("cantidad", 1)
        val imagen = intent.getIntExtra("imagen", 0)

        // 🔹 Vistas
        val txtNombre = findViewById<TextView>(R.id.txtNombreDetalle)
        val txtPrecio = findViewById<TextView>(R.id.txtPrecioDetalle)
        val txtCantidad = findViewById<TextView>(R.id.txtCantidadDetalle)
        val txtDescripcion = findViewById<TextView>(R.id.txtDescripcion)
        val imgProducto = findViewById<ImageView>(R.id.imgProductoDetalle)

        // 🔹 Asignación
        txtNombre.text = nombre
        txtPrecio.text = "S/ %.2f".format(precio)
        txtCantidad.text = "Cantidad: $cantidad"

        if (imagen != 0) {
            imgProducto.setImageResource(imagen)
        }

        // 🔹 Descripción dinámica
        txtDescripcion.text = when (nombre) {
            "Teddy Bear" -> "Suave y adorable oso de peluche, perfecto para regalar."
            "Peluche" -> "Peluche tierno y de excelente calidad."
            "Muñecas" -> "Muñecas para estimular la imaginación."
            "Barbie" -> "Muñeca moderna y elegante."
            "Barco" -> "Barco de juguete para aventuras."
            "Carro Clásico" -> "Auto clásico de colección."
            "Camión" -> "Camión resistente para juegos."
            "Dream House" -> "Casa de ensueño para historias."
            "Mansión" -> "Mansión de lujo en miniatura."
            "Robot" -> "Robot futurista e interactivo."
            else -> "Producto de excelente calidad"
        }
    }

    // 🔙 Botón atrás
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}