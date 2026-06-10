package com.ucsm.campusmarket.ui.screens.auth


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ucsm.campusmarket.ui.components.ActionButton
import com.ucsm.campusmarket.ui.components.AppTopBar
import com.ucsm.campusmarket.ui.components.FormField
import com.ucsm.campusmarket.ui.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit,
    onBack: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val error = authViewModel.error.value

    Scaffold(
        topBar = {
            AppTopBar(title = "Crear cuenta")
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            FormField(
                label = "Correo electrónico",
                value = email,
                onValueChange = {
                    email = it
                }
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text("Contraseña")
                },
                modifier = Modifier.fillMaxWidth()
            )

            error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }

            ActionButton(
                text = "Registrarse",
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                    authViewModel.register(
                        email = email,
                        password = password
                    ) {
                        onRegisterSuccess()
                    }

                }
            )

            TextButton(
                onClick = onBack
            ) {
                Text("Volver al inicio de sesión")
            }

        }

    }

}