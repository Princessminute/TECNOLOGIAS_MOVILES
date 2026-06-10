package com.ucsm.campusmarket

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.FirebaseApp
import com.ucsm.campusmarket.data.local.AppDatabase
import com.ucsm.campusmarket.data.repository.ArticuloRepository
import com.ucsm.campusmarket.data.repository.AuthRepository
import com.ucsm.campusmarket.ui.navigation.NavGraph
import com.ucsm.campusmarket.ui.theme.CampusMarketTheme
import com.ucsm.campusmarket.ui.viewmodel.ArticuloViewModel
import com.ucsm.campusmarket.ui.viewmodel.ArticuloViewModelFactory
import com.ucsm.campusmarket.ui.viewmodel.AuthViewModel
import com.ucsm.campusmarket.ui.viewmodel.AuthViewModelFactory
import com.google.firebase.messaging.FirebaseMessaging
import android.os.Build
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = FirebaseApp.initializeApp(this)

        if (app != null) {
            Log.d("FIREBASE", "Firebase inicializado correctamente")
        } else {
            Log.e("FIREBASE", "Firebase NO se inicializó")
        }

        val database = AppDatabase.getDatabase(applicationContext)
        val articuloRepository = ArticuloRepository(database.articuloDao())

        val authRepository = AuthRepository()

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    Log.d("FCM", "TOKEN: ${task.result}")

                } else {

                    Log.e("FCM", "Error obteniendo token", task.exception)

                }

            }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            requestPermissions(
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                100
            )

        }
        setContent {

            CampusMarketTheme {

                val articuloViewModel: ArticuloViewModel = viewModel(
                    factory = ArticuloViewModelFactory(articuloRepository)
                )

                val authViewModel: AuthViewModel = viewModel(
                    factory = AuthViewModelFactory(authRepository)
                )

                NavGraph(
                    viewModel = articuloViewModel,
                    authViewModel = authViewModel
                )

            }

        }
    }
}