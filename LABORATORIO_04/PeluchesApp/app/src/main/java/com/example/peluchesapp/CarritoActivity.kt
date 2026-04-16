package com.example.peluchesapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class CarritoActivity : AppCompatActivity() {

    private lateinit var txtTotal: TextView
    private lateinit var adapter: CarritoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarCarrito)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recycler = findViewById<RecyclerView>(R.id.recyclerCarrito)
        txtTotal = findViewById(R.id.txtTotal)
        val btnPagar = findViewById<Button>(R.id.btnPagar)

        adapter = CarritoAdapter(CarritoGlobal.lista) {
            calcularTotal()
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        calcularTotal()

        btnPagar.setOnClickListener {
            Toast.makeText(this, "Compra realizada 🛒", Toast.LENGTH_SHORT).show()
            CarritoGlobal.lista.clear()
            adapter.notifyDataSetChanged()
            calcularTotal()
            finish()
        }
    }

    private fun calcularTotal() {
        val total = CarritoGlobal.lista.sumOf { it.precio * it.cantidad }
        txtTotal.text = "Total: S/ %.2f".format(total)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}