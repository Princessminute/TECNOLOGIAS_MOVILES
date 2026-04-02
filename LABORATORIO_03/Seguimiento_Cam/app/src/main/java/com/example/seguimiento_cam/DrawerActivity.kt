package com.example.seguimiento_cam

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar

class DrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var progressBar: ProgressBar
    private lateinit var textViewCarga: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // 🔒 BLOQUEAR MENÚ AL INICIO
        navView.isEnabled = false
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        navView.setNavigationItemSelectedListener(this)

        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.drawer_open, R.string.drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        progressBar = findViewById(R.id.progressBar)
        textViewCarga = findViewById(R.id.textViewCarga)

        simularCarga()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                startActivity(Intent(this, SegundaActividad::class.java))
                finish()
            }
            R.id.nav_settings -> {
                startActivity(Intent(this, ConversionMonedasActivity::class.java))
                finish()
            }
        }
        drawerLayout.closeDrawers()
        return true
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

                // 🔓 DESBLOQUEAR MENÚ
                navView.isEnabled = true
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }

        }.start()
    }
}