package com.ucsm.basededatos.data

import android.util.Log
import kotlinx.coroutines.flow.Flow

class ArticuloRepository(private val dao: ArticuloDao) {

    suspend fun insertar(articulo: Articulo) {
        Log.d("REPOSITORY", "Insertando artículo: $articulo")
        dao.insertar(articulo)
    }

    suspend fun buscarPorCodigo(codigo: Int): Articulo? {
        Log.d("REPOSITORY", "Buscando artículo con código: $codigo")
        return dao.buscarPorCodigo(codigo)
    }

    suspend fun actualizar(articulo: Articulo): Int {
        Log.d("REPOSITORY", "Actualizando artículo: $articulo")
        return dao.actualizar(articulo)
    }

    suspend fun eliminarPorCodigo(codigo: Int): Int {
        Log.d("REPOSITORY", "Eliminando artículo con código: $codigo")
        return dao.eliminarPorCodigo(codigo)
    }

    fun listarTodos(): Flow<List<Articulo>> {
        Log.d("REPOSITORY", "Listando todos los artículos")
        return dao.listarTodos()
    }
}