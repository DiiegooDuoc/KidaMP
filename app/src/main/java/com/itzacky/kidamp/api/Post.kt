package com.itzacky.kidamp.api

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Post(
    val id_cancion: Int,
    val nombre_cancion: String,
    val autor_cancion: String,
    val album_cancion: String,

    @SerializedName("body")
    val content: String
)