package com.example.seguimiento_cam

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import android.widget.TextView
class SegundaActividad : AppCompatActivity() {

    private val TIMER_RUNTIME = 10000
    private var nbActivo = false
    private lateinit var nProgressBar: ProgressBar
    private lateinit var txtEstado: TextView
    private var yaMostrado = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda_actividad)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        txtEstado = findViewById(R.id.txtEstado)
        nProgressBar = findViewById(R.id.progressBar)

        val timerThread = Thread {
            nbActivo = true
            try {
                var tiempo = 0
                while (nbActivo && tiempo < TIMER_RUNTIME) {
                    Thread.sleep(200)
                    tiempo += 200
                    actualizarProgress(tiempo)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        timerThread.start()
    }

    // 🔙 BOTÓN ←
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }



    private fun actualizarProgress(timePassed: Int) {
        val progress = nProgressBar.max * timePassed / TIMER_RUNTIME

        runOnUiThread {
            nProgressBar.progress = progress

            if (progress >= nProgressBar.max && !yaMostrado) {
                yaMostrado = true
                nbActivo = false

                txtEstado.text = "Carga completa"
            }
        }
    }
    }
