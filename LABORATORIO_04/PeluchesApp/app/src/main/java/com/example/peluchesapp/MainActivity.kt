package com.example.peluchesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.appbar.MaterialToolbar


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ✅ Toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarMain)
        setSupportActionBar(toolbar)

        val recycler = findViewById<RecyclerView>(R.id.recyclerProductos)
        val btnCarrito = findViewById<FloatingActionButton>(R.id.btnCarrito)

        val listaProductos = listOf(
            Producto("Teddy Bear", 20.0, R.drawable.p1, (15..50).random()),
            Producto("Peluche", 22.0, R.drawable.p2, (15..50).random()),
            Producto("Muñecas", 25.0, R.drawable.p3, (15..50).random()),
            Producto("Barbie", 30.0, R.drawable.p4, (15..50).random()),
            Producto("Barco", 18.0, R.drawable.p5, (15..50).random()),
            Producto("Carro Clásico", 27.0, R.drawable.p6, (15..50).random()),
            Producto("Camión", 35.0, R.drawable.p7, (15..50).random()),
            Producto("Dream House", 40.0, R.drawable.p8, (15..50).random()),
            Producto("Mansión", 19.0, R.drawable.p9, (15..50).random()),
            Producto("Robot", 50.0, R.drawable.p10, (15..50).random())
        )

        val adapter = ProductoAdapter(listaProductos) { producto ->

            val existente = CarritoGlobal.lista.find { it.nombre == producto.nombre }

            if (existente != null) {
                existente.cantidad++
            } else {
                val nuevo = producto.copy(cantidad = 1)
                CarritoGlobal.lista.add(nuevo)
            }

            Toast.makeText(this, "Agregado al carrito", Toast.LENGTH_SHORT).show()
        }

        // 🔥 GRID DINÁMICO
        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val density = displayMetrics.density

        val itemWidthDp = 160
        val itemWidthPx = itemWidthDp * density

        val spanCount = (screenWidth / itemWidthPx)
            .toInt()
            .coerceIn(2, 4)

        recycler.layoutManager = GridLayoutManager(this, spanCount)
        recycler.adapter = adapter

        recycler.addItemDecoration(EspaciadoGrid(16))
        recycler.setHasFixedSize(true)

        btnCarrito.setOnClickListener {
            startActivity(Intent(this, CarritoActivity::class.java))
        }
    }

    class EspaciadoGrid(private val espacio: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: android.graphics.Rect,
            view: android.view.View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.set(espacio, espacio, espacio, espacio)
        }
    }
}