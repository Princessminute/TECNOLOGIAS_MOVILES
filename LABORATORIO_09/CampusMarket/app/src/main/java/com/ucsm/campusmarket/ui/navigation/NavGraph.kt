package com.ucsm.campusmarket.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.ucsm.campusmarket.core.Resource
import com.ucsm.campusmarket.ui.screens.*
import com.ucsm.campusmarket.ui.screens.auth.LoginScreen
import com.ucsm.campusmarket.ui.screens.auth.RegisterScreen
import com.ucsm.campusmarket.ui.viewmodel.ArticuloViewModel
import com.ucsm.campusmarket.ui.viewmodel.AuthViewModel

@Composable
fun NavGraph(
    viewModel: ArticuloViewModel,
    authViewModel: AuthViewModel
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        // 🔥 SPLASH
        composable("splash") {

            LaunchedEffect(Unit) {

                val destino =
                    if (authViewModel.isLogged()) "lista" else "login"

                navController.navigate(destino) {
                    popUpTo("splash") { inclusive = true }
                }
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        // 🔐 LOGIN
        composable("login") {

            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate("lista") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }

        // 🧾 REGISTER
        composable("register") {

            RegisterScreen(
                authViewModel = authViewModel,
                onRegisterSuccess = {
                    navController.navigate("lista") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // 📋 LISTA (CON LOGOUT)
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
                        Text(message)
                    }
                }

                is Resource.Success -> {

                    val articulos = (state as Resource.Success).data

                    ListaArticulosScreen(

                        articulos = articulos,

                        onEdit = { navController.navigate("editar/${it.id}") },
                        onDelete = { viewModel.eliminar(it) },
                        onAdd = { navController.navigate("agregar") },
                        onDetail = { navController.navigate("detalle/${it.id}") },

                        // 🔥 LOGOUT CORRECTO
                        onLogout = {
                            authViewModel.logout {
                                navController.navigate("login") {
                                    popUpTo("lista") { inclusive = true }
                                }
                            }
                        }
                    )
                }
            }
        }

        // ➕ AGREGAR
        composable("agregar") {

            AgregarArticuloScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        // ✏️ EDITAR
        composable(
            route = "editar/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id")

            val state by viewModel.uiState.collectAsState()

            val articulo = (state as? Resource.Success)
                ?.data
                ?.find { it.id == id }

            articulo?.let {

                EditarArticuloScreen(
                    articulo = it,
                    onUpdate = {
                        viewModel.actualizar(it)
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }

        // 🔎 DETALLE
        composable(
            route = "detalle/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id")

            val state by viewModel.uiState.collectAsState()

            val articulo = (state as? Resource.Success)
                ?.data
                ?.find { it.id == id }

            articulo?.let {

                ArticuloDetailScreen(
                    articulo = it,
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