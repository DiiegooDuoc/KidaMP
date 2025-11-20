package com.itzacky.kidamp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class FormularioModel {

    var nombre by mutableStateOf("")
    var album by mutableStateOf("")
    var artista by mutableStateOf("")
    //Una vez que se escribe la canción, esto BORRA toda la vaina loca
    //gracias al mutableStateOf :D

}