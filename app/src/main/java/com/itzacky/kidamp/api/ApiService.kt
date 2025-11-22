package com.itzacky.kidamp.api

import com.itzacky.kidamp.model.Cancion
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("obtener_canciones.php")
    suspend fun getCanciones(): Response<List<Cancion>>
}
