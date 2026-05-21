@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.ucsm.campusmarket.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ucsm.campusmarket.data.local.ArticuloEntity
import com.ucsm.campusmarket.ui.components.ArticuloItem
import com.ucsm.campusmarket.ui.components.EmptyState
import com.ucsm.campusmarket.ui.theme.*

@Composable
fun ListaArticulosScreen(
    articulos: List<ArticuloEntity>,
    onDelete: (ArticuloEntity) -> Unit,
    onEdit: (ArticuloEntity) -> Unit,
    onAdd: () -> Unit,
    onDetail: (ArticuloEntity) -> Unit   // 👈 NUEVO
) {

    Scaffold(

        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Campus Market",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = DarkText
                        )
                        Text(
                            text = "Tus productos disponibles",
                            style = MaterialTheme.typography.labelMedium,
                            color = MediumText
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MintLighter
                )
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdd,
                containerColor = MintSoft,
                contentColor = DarkText
            ) {
                Text("+", style = MaterialTheme.typography.titleLarge)
            }
        }

    ) { padding ->

        if (articulos.isEmpty()) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                EmptyState("No hay artículos aún")
            }

        } else {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Explora tus productos",
                    style = MaterialTheme.typography.titleMedium,
                    color = DarkText
                )

                Spacer(Modifier.height(16.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    items(articulos) { item ->
                        ArticuloItem(
                            articulo = item,
                            onEdit = onEdit,
                            onDelete = onDelete,
                            onClick = onDetail   // 👈 AQUÍ VA BIEN
                        )
                    }
                }
            }
        }
    }
}