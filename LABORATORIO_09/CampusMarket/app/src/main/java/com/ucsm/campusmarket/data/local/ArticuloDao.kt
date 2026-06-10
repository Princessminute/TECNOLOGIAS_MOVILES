package com.ucsm.campusmarket.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticuloDao {

    // LISTAR TODO
    @Query("SELECT * FROM articulos ORDER BY id DESC")
    fun getAllArticulos(): Flow<List<ArticuloEntity>>

    // INSERTAR (falla si el nombre ya existe por UNIQUE)
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertArticulo(articulo: ArticuloEntity)

    // ACTUALIZAR
    @Update
    suspend fun updateArticulo(articulo: ArticuloEntity)

    // ELIMINAR
    @Delete
    suspend fun deleteArticulo(articulo: ArticuloEntity)

    // BUSCAR POR ID (editar)
    @Query("SELECT * FROM articulos WHERE id = :id")
    suspend fun getArticuloById(id: Int): ArticuloEntity?

    // VERIFICAR SI EXISTE POR NOMBRE (para validar en UI/ViewModel)
    @Query("SELECT * FROM articulos WHERE nombre = :nombre LIMIT 1")
    suspend fun existePorNombre(nombre: String): ArticuloEntity?
}