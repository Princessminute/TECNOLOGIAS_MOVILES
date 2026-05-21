package com.ucsm.campusmarket.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ucsm.campusmarket.data.repository.ArticuloRepository

class ArticuloViewModelFactory(
    private val repository: ArticuloRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticuloViewModel::class.java)) {
            return ArticuloViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}