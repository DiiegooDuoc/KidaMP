package com.itzacky.kidamp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class FormularioModel {

    var nombre by mutableStateOf("")
    var album by mutableStateOf("")
    var artista by mutableStateOf("")
    var terminos by mutableStateOf("")
}