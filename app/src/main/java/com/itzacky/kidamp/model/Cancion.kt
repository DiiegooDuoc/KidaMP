package com.itzacky.kidamp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Songs")
data class Cancion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var nombre_cancion: String,
    var autor_cancion: String,
    var album_cancion: String,

    /**
     * "var" en caso de que el nombre de la cancion cambie
     * o si cambia el nombre del artista como por ejemplo:
     *
     * Misfits pasó a "The Misfits" (por razones legales).
     */
)