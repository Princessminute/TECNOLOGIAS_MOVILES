package com.example.seguimiento_cam

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var textViewCarga: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        textViewCarga = findViewById(R.id.textViewCarga)

        // 🔒 BLOQUEAR BOTONES AL INICIO
        habilitarBotones(false)

        simularCarga()
    }

    // Ir a SegundaActividad
    fun onClick(view: View) {
        startActivity(Intent(this, SegundaActividad::class.java))
    }

    // Ir a Conversión de monedas
    fun abrirConversionMonedas(view: View) {
        startActivity(Intent(this, ConversionMonedasActivity::class.java))
    }

    // Ir al Drawer
    fun abrirDrawer(view: View) {
        startActivity(Intent(this, DrawerActivity::class.java))
    }

    private fun simularCarga() {
        Thread {
            var progreso = 0

            while (progreso <= 100) {
                Thread.sleep(40)
                progreso++

                runOnUiThread {
                    progressBar.progress = progreso
                }
            }

            runOnUiThread {
                textViewCarga.text = "Carga completa"
                progressBar.visibility = View.GONE

                // 🔓 DESBLOQUEAR BOTONES
                habilitarBotones(true)
            }

        }.start()
    }

    private fun habilitarBotones(estado: Boolean) {
        findViewById<Button>(R.id.btnMostrar).isEnabled = estado
        findViewById<Button>(R.id.btnConversionMonedas).isEnabled = estado
        findViewById<Button>(R.id.btnDrawer).isEnabled = estado
    }
}