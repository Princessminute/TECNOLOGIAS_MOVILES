package com.ucsm.campusmarket.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "articulos",
    indices = [Index(value = ["nombre"], unique = true)]
)
data class ArticuloEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,

    val descripcion: String,

    val precio: Double
)