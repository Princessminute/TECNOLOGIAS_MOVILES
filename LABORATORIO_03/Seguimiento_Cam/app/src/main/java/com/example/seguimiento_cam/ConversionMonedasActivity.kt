package com.example.seguimiento_cam

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import android.widget.ProgressBar
class ConversionMonedasActivity : AppCompatActivity() {

    private lateinit var editTextCantidad: EditText
    private lateinit var spinnerOrigen: Spinner
    private lateinit var spinnerDestino: Spinner
    private lateinit var textViewResultado: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var textViewCarga: TextView
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
        setContentView(R.layout.activity_conversion_monedas)

        // 🔥 TOOLBAR
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        editTextCantidad = findViewById(R.id.editTextText)
        spinnerOrigen = findViewById(R.id.spinnerOrigen)
        spinnerDestino = findViewById(R.id.spinnerDestino)
        textViewResultado = findViewById(R.id.textViewResultado)
        progressBar = findViewById(R.id.progressBar)
        textViewCarga = findViewById(R.id.textViewCarga)

        simularCarga()
        val monedas = tasas.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, monedas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerOrigen.adapter = adapter
        spinnerDestino.adapter = adapter
    }

    // 👇 MANEJO DEL BOTÓN ←
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun miClicManejador(view: View) {
        if (view.id == R.id.btnConvertir) {
            convertirMoneda()
        }
    }

    private fun convertirMoneda() {
        val cantidadTexto = editTextCantidad.text.toString().trim()

        if (cantidadTexto.isEmpty()) {
            Toast.makeText(this, "Ingresa una cantidad", Toast.LENGTH_LONG).show()
            return
        }

        val cantidad = cantidadTexto.toFloatOrNull()
        if (cantidad == null || cantidad <= 0) {
            Toast.makeText(this, "Número inválido", Toast.LENGTH_LONG).show()
            return
        }

        val origen = spinnerOrigen.selectedItem.toString()
        val destino = spinnerDestino.selectedItem.toString()

        val resultado = (cantidad / tasas[origen]!!) * tasas[destino]!!

        textViewResultado.text = "%.2f %s = %.2f %s".format(cantidad, origen, resultado, destino)
    }
    private fun simularCarga() {
        Thread {
            var progreso = 0

            while (progreso <= 100) {
                Thread.sleep(50)
                progreso++

                runOnUiThread {
                    progressBar.progress = progreso
                }
            }

            runOnUiThread {
                textViewCarga.text = "Carga completa"
                progressBar.visibility = View.GONE
            }

        }.start()
    }
}