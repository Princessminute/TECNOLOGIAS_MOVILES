package com.ucsm.fichero

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ucsm.fichero.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val FILE_NAME = "textfile.txt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            onClickSave()
        }

        binding.btnLoad.setOnClickListener {
            onClickLoad()
        }

        binding.btnSaveExternal.setOnClickListener {
            onClickSaveExternal()
        }
    }

    private fun onClickSave() {
        val texto = binding.etText.text.toString()

        try {
            openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use { fos ->
                fos.write(texto.toByteArray())
            }

            Toast.makeText(
                this,
                "Archivo guardado correctamente!",
                Toast.LENGTH_SHORT
            ).show()

            binding.etText.setText("")

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(
                this,
                "Error al guardar: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun onClickLoad() {
        try {
            val contenido = openFileInput(FILE_NAME)
                .bufferedReader()
                .use { it.readText() }

            binding.etText.setText(contenido)

            Toast.makeText(
                this,
                "Archivo cargado satisfactoriamente!",
                Toast.LENGTH_SHORT
            ).show()

        } catch (e: java.io.FileNotFoundException) {
            Toast.makeText(
                this,
                "El archivo no existe aún",
                Toast.LENGTH_SHORT
            ).show()

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(
                this,
                "Error al cargar: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun onClickSaveExternal() {
        val texto = binding.etText.text.toString()

        if (texto.isBlank()) {
            Toast.makeText(this, "No puedes guardar vacío", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val file = File(getExternalFilesDir(null), "external_file.txt")
            file.writeText(texto)

            Toast.makeText(
                this,
                "Guardado en almacenamiento externo",
                Toast.LENGTH_SHORT
            ).show()

            binding.etText.setText("")

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}