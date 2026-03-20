package com.example.registroestudiante;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nombre, apellidos, edad;
    Button registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre = findViewById(R.id.etNombre);
        apellidos = findViewById(R.id.etApellidos);
        edad = findViewById(R.id.etEdad);
        registrar = findViewById(R.id.btnRegistrar);

        registrar.setOnClickListener(v -> {

            String n = nombre.getText().toString();
            String a = apellidos.getText().toString();
            String e = edad.getText().toString();

            Toast.makeText(this,
                    "Estudiante: " + n + " " + a + " Edad: " + e,
                    Toast.LENGTH_LONG).show();
        });
    }
}