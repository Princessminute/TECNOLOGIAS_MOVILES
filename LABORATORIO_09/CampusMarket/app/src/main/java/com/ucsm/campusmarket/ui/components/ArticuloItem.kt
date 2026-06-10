package com.ucsm.campusmarket.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ucsm.campusmarket.data.local.ArticuloEntity
import com.ucsm.campusmarket.ui.theme.*

@Composable
fun ArticuloItem(
    articulo: ArticuloEntity,
    onClick: (ArticuloEntity) -> Unit,
    onEdit: (ArticuloEntity) -> Unit,
    onDelete: (ArticuloEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick(articulo) },   // 👈 CLAVE
        colors = CardDefaults.cardColors(
            containerColor = WarmWhite
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            Text(
                text = articulo.nombre,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Precio: S/ ${articulo.precio}",
                style = MaterialTheme.typography.labelMedium,
                color = MediumText
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row {

                Button(
                    onClick = { onEdit(articulo) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MintSoft
                    )
                ) {
                    Text("Editar")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { onDelete(articulo) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MintLight
                    )
                ) {
                    Text("Eliminar")
                }
            }
        }
    }
}