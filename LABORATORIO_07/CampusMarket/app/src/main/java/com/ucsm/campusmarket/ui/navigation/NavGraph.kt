package com.ucsm.campusmarket.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.ucsm.campusmarket.core.Resource
import com.ucsm.campusmarket.ui.viewmodel.ArticuloViewModel
import com.ucsm.campusmarket.ui.screens.*

@Composable
fun NavGraph(viewModel: ArticuloViewModel) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "lista"
    ) {

        composable("lista") {

            val state by viewModel.uiState.collectAsState()

            when (state) {

                is Resource.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is Resource.Error -> {
                    val message = (state as Resource.Error).message

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = message)
                    }
                }

                is Resource.Success -> {

                    val articulos = (state as Resource.Success).data

                    ListaArticulosScreen(
                        articulos = articulos,
                        onEdit = { navController.navigate("editar/${it.id}") },
                        onDelete = { viewModel.eliminar(it) },
                        onAdd = { navController.navigate("agregar") },
                        onDetail = { navController.navigate("detalle/${it.id}") }
                    )
                }
            }
        }

        composable("agregar") {
            AgregarArticuloScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "editar/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id")
            val state by viewModel.uiState.collectAsState()

            val articulo = (state as? Resource.Success)
                ?.data
                ?.find { it.id == id }

            if (articulo != null) {
                EditarArticuloScreen(
                    articulo = articulo,
                    onUpdate = {
                        viewModel.actualizar(it)
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }

        composable(
            route = "detalle/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id")
            val state by viewModel.uiState.collectAsState()

            val articulo = (state as? Resource.Success)
                ?.data
                ?.find { it.id == id }

            if (articulo != null) {
                ArticuloDetailScreen(
                    articulo = articulo,
                    onEdit = { navController.navigate("editar/${it.id}") },
                    onDelete = {
                        viewModel.eliminar(it)
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}