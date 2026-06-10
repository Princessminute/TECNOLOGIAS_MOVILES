package com.ucsm.campusmarket.data.repository

import com.ucsm.campusmarket.data.local.ArticuloDao
import com.ucsm.campusmarket.data.local.ArticuloEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ArticuloRepository(
    private val dao: ArticuloDao
) {

    // 📌 STREAM REACTIVO
    val articulos: Flow<List<ArticuloEntity>> = dao.getAllArticulos()

    // ➕ INSERTAR
    suspend fun insertarArticulo(articulo: ArticuloEntity) {
        withContext(Dispatchers.IO) {
            dao.insertArticulo(articulo)
        }
    }

    // ✏️ ACTUALIZAR
    suspend fun actualizarArticulo(articulo: ArticuloEntity) {
        withContext(Dispatchers.IO) {
            dao.updateArticulo(articulo)
        }
    }

    // 🗑 ELIMINAR
    suspend fun eliminarArticulo(articulo: ArticuloEntity) {
        withContext(Dispatchers.IO) {
            dao.deleteArticulo(articulo)
        }
    }

    // 🔍 BUSCAR POR ID
    suspend fun obtenerPorId(id: Int): ArticuloEntity? {
        return withContext(Dispatchers.IO) {
            dao.getArticuloById(id)
        }
    }

    // 🔍 VALIDACIÓN
    suspend fun existePorNombre(nombre: String): ArticuloEntity? {
        return withContext(Dispatchers.IO) {
            dao.existePorNombre(nombre)
        }
    }
}