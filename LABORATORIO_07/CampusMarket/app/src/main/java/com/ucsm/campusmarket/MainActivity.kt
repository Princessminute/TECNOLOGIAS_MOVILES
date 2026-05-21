package com.ucsm.campusmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ucsm.campusmarket.data.local.AppDatabase
import com.ucsm.campusmarket.data.repository.ArticuloRepository
import com.ucsm.campusmarket.ui.navigation.NavGraph
import com.ucsm.campusmarket.ui.theme.CampusMarketTheme
import com.ucsm.campusmarket.ui.viewmodel.ArticuloViewModel
import com.ucsm.campusmarket.ui.viewmodel.ArticuloViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = ArticuloRepository(database.articuloDao())

        setContent {
            CampusMarketTheme {

                val viewModel: ArticuloViewModel = viewModel(
                    factory = ArticuloViewModelFactory(repository)
                )

                NavGraph(viewModel = viewModel)
            }
        }
    }
}