package com.ucsm.campusmarket.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ucsm.campusmarket.data.local.ArticuloEntity
import com.ucsm.campusmarket.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticuloDetailScreen(
    articulo: ArticuloEntity,
    onEdit: (ArticuloEntity) -> Unit,
    onDelete: (ArticuloEntity) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Detalle",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("← Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MintLighter
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // 🟩 CARD PRINCIPAL
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = WarmWhite
                ),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        text = articulo.nombre,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "S/ ${articulo.precio}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MediumText,
                        fontWeight = FontWeight.SemiBold
                    )

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        text = "Información del producto",
                        style = MaterialTheme.typography.labelLarge,
                        color = DarkText
                    )

                    Text(
                        text = "ID: ${articulo.id}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MediumText
                    )
                }
            }

            // 🎯 BOTONES ACCIÓN (más pro)
            Button(
                onClick = { onEdit(articulo) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MintSoft,
                    contentColor = DarkText
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Editar artículo")
            }

            OutlinedButton(
                onClick = { onDelete(articulo) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Eliminar artículo")
            }
        }
    }
}