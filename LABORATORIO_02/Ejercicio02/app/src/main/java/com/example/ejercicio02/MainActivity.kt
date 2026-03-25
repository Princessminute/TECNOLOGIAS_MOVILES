package com.example.ejercicio02

import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.Toast
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pizzas = listOf(
            Pizza("Margarita", R.drawable.margarita),
            Pizza("Pepperoni", R.drawable.pepperoni),
            Pizza("Hawaiana", R.drawable.hawaiana),
            Pizza("Cuatro Quesos", R.drawable.cuatroquesos)
        )

        val gridView = findViewById<GridView>(R.id.gridView)
        gridView.adapter = PizzaAdapter(this, pizzas)
    }
}