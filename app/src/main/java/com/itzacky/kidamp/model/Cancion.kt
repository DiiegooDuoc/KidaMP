package com.itzacky.kidamp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Songs")
data class Cancion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre_cancion: String,
    val autor_cancion: String,
    val album_cancion: String,
)