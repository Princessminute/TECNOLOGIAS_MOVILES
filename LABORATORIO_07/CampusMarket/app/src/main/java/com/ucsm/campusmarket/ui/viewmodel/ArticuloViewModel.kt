package com.ucsm.campusmarket.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucsm.campusmarket.core.Resource
import com.ucsm.campusmarket.data.local.ArticuloEntity
import com.ucsm.campusmarket.data.repository.ArticuloRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ArticuloViewModel(
    private val repository: ArticuloRepository
) : ViewModel() {

    // 📌 Estado UI de lista (LOADING / SUCCESS / ERROR)
    private val _uiState =
        MutableStateFlow<Resource<List<ArticuloEntity>>>(Resource.Loading)

    val uiState: StateFlow<Resource<List<ArticuloEntity>>> = _uiState

    init {
        cargarArticulos()
    }

    private fun cargarArticulos() {
        viewModelScope.launch {
            repository.articulos
                .catch { e ->
                    _uiState.value = Resource.Error(e.message ?: "Error al cargar datos")
                }
                .collect { list ->
                    _uiState.value = Resource.Success(list)
                }
        }
    }

    // 🔴 ERROR GLOBAL SIMPLE (para formularios)
    var error = mutableStateOf<String?>(null)
        private set

    // ➕ INSERTAR
    fun insertar(articulo: ArticuloEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val existe = repository.existePorNombre(articulo.nombre)

                if (existe != null) {
                    error.value = "Ya existe un artículo con ese nombre"
                    return@launch
                }

                repository.insertarArticulo(articulo)
                error.value = null

            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }

    // ✏️ ACTUALIZAR
    fun actualizar(articulo: ArticuloEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.actualizarArticulo(articulo)
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }

    // 🗑 ELIMINAR
    fun eliminar(articulo: ArticuloEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.eliminarArticulo(articulo)
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }

    fun limpiarError() {
        error.value = null
    }
}