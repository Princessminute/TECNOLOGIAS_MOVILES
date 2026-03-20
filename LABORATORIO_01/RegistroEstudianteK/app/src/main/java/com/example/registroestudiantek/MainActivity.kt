package com.example.registroestudiantek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nombre = findViewById<EditText>(R.id.etNombre)
        val apellidos = findViewById<EditText>(R.id.etApellidos)
        val edad = findViewById<EditText>(R.id.etEdad)
        val registrar = findViewById<Button>(R.id.btnRegistrar)

        registrar.setOnClickListener {

            val n = nombre.text.toString()
            val a = apellidos.text.toString()
            val e = edad.text.toString()

            Toast.makeText(
                this,
                "Estudiante: $n $a Edad: $e",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}