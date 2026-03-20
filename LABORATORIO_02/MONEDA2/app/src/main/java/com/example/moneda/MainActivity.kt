package com.example.moneda

import android.graphics.Color //colores de codigo
import android.os.Bundle //recrea
import android.view.View //CLICKS
import android.widget.*
import androidx.appcompat.app.AppCompatActivity //action bar
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var editTextCantidad: EditText
    private lateinit var radioGrupo: RadioGroup
    private lateinit var textViewResultado: TextView
    private val tipoCambio = 3.65f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextCantidad = findViewById(R.id.editTextText)
        radioGrupo = findViewById(R.id.radioGrupo)
        textViewResultado = findViewById(R.id.textViewResultado)

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

        when (radioGrupo.checkedRadioButtonId) {
            R.id.radio0 -> { // Dólares a Soles
                val soles = cantidad * tipoCambio
                mostrarResultado("%.2f dólares = %.2f soles".format(cantidad, soles))
            }
            R.id.radio1 -> { // Soles a Dólares
                val dolares = cantidad / tipoCambio
                mostrarResultado("%.2f soles = %.2f dólares".format(cantidad, dolares))
            }
            else -> mostrarError("Selecciona una opción")
        }
    }

    private fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        editTextCantidad.requestFocus()
    }

    private fun mostrarResultado(mensaje: String) {
        textViewResultado.text = mensaje
    }
}