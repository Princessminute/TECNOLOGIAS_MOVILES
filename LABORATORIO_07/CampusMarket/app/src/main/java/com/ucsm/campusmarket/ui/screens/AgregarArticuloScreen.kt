package com.ucsm.campusmarket.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ucsm.campusmarket.data.local.ArticuloEntity
import com.ucsm.campusmarket.ui.components.FormField
import com.ucsm.campusmarket.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarArticuloScreen(
    viewModel: com.ucsm.campusmarket.ui.viewmodel.ArticuloViewModel,
    onBack: () -> Unit
) {

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    var errorNombre by remember { mutableStateOf("") }
    var errorDescripcion by remember { mutableStateOf("") }
    var errorPrecio by remember { mutableStateOf("") }

    val error = viewModel.error.value

    // 🔴 ALERT GLOBAL
    if (error != null) {
        AlertDialog(
            onDismissRequest = { viewModel.limpiarError() },
            confirmButton = {
                TextButton(onClick = { viewModel.limpiarError() }) {
                    Text("OK")
                }
            },
            title = { Text("Error") },
            text = { Text(error) }
        )
    }

    fun validar(): Boolean {
        var ok = true

        errorNombre = ""
        errorDescripcion = ""
        errorPrecio = ""

        if (nombre.isBlank()) {
            errorNombre = "El nombre es obligatorio"
            ok = false
        }

        if (descripcion.isBlank()) {
            errorDescripcion = "La descripción es obligatoria"
            ok = false
        }

        val precioDouble = precio.toDoubleOrNull()

        if (precio.isBlank()) {
            errorPrecio = "El precio es obligatorio"
            ok = false
        } else if (precioDouble == null || precioDouble <= 0) {
            errorPrecio = "Precio inválido"
            ok = false
        }

        return ok
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Nuevo artículo",
                        fontWeight = FontWeight.Bold,
                        color = DarkText
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MintLighter
                )
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.TopCenter
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = WarmWhite
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        "Completa los datos",
                        style = MaterialTheme.typography.titleMedium,
                        color = DarkText
                    )

                    FormField(
                        label = "Nombre",
                        value = nombre,
                        onValueChange = { nombre = it }
                    )

                    if (errorNombre.isNotEmpty()) {
                        Text(errorNombre, color = MaterialTheme.colorScheme.error)
                    }

                    FormField(
                        label = "Descripción",
                        value = descripcion,
                        onValueChange = { descripcion = it }
                    )

                    if (errorDescripcion.isNotEmpty()) {
                        Text(errorDescripcion, color = MaterialTheme.colorScheme.error)
                    }

                    FormField(
                        label = "Precio",
                        value = precio,
                        onValueChange = { precio = it }
                    )

                    if (errorPrecio.isNotEmpty()) {
                        Text(errorPrecio, color = MaterialTheme.colorScheme.error)
                    }

                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = {
                            if (validar()) {
                                viewModel.insertar(
                                    ArticuloEntity(
                                        nombre = nombre.trim(),
                                        descripcion = descripcion.trim(),
                                        precio = precio.toDouble()
                                    )
                                )

                                if (viewModel.error.value == null) {
                                    onBack()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MintSoft,
                            contentColor = DarkText
                        )
                    ) {
                        Text("Guardar")
                    }

                    OutlinedButton(
                        onClick = onBack,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Cancelar")
                    }
                }
            }
        }
    }
}