package com.example.moneda

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextCantidad: EditText
    private lateinit var spinnerOrigen: Spinner
    private lateinit var spinnerDestino: Spinner
    private lateinit var textViewResultado: TextView

    // Tipo de cambio respecto al DÓLAR (base)
    private val tasas = mapOf(
        "USD" to 1.0f,
        "PEN" to 3.65f,
        "EUR" to 0.92f,
        "GBP" to 0.78f,
        "INR" to 83.0f,
        "BRL" to 5.0f,
        "MXN" to 17.0f,
        "CNY" to 7.2f,
        "JPY" to 150.0f
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextCantidad = findViewById(R.id.editTextText)
        spinnerOrigen = findViewById(R.id.spinnerOrigen)
        spinnerDestino = findViewById(R.id.spinnerDestino)
        textViewResultado = findViewById(R.id.textViewResultado)

        val monedas = tasas.keys.toList()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, monedas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerOrigen.adapter = adapter
        spinnerDestino.adapter = adapter

        editTextCantidad.setBackgroundColor(Color.parseColor("#D6F0F9"))
        textViewResultado.setBackgroundColor(Color.parseColor("#A8E0F0"))
        findViewById<Button>(R.id.btnConvertir).setBackgroundColor(Color.parseColor("#8DD7F5"))
    }

    fun miClicManejador(view: View) {
        if (view.id == R.id.btnConvertir) {
            convertirMoneda()
        }
    }

    private fun convertirMoneda() {
        val cantidadTexto = editTextCantidad.text.toString().trim()

        if (cantidadTexto.isEmpty()) {
            mostrarError("Ingresa una cantidad")
            return
        }

        val cantidad = cantidadTexto.toFloatOrNull()
        if (cantidad == null || cantidad <= 0) {
            mostrarError("Número inválido")
            return
        }

        val origen = spinnerOrigen.selectedItem.toString()
        val destino = spinnerDestino.selectedItem.toString()

        val tasaOrigen = tasas[origen]!!
        val tasaDestino = tasas[destino]!!

        // Convertir a USD primero, luego al destino
        val enDolares = cantidad / tasaOrigen
        val resultado = enDolares * tasaDestino

        mostrarResultado("%.2f %s = %.2f %s".format(cantidad, origen, resultado, destino))
    }

    private fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        editTextCantidad.requestFocus()
    }

    private fun mostrarResultado(mensaje: String) {
        textViewResultado.text = mensaje
    }
}