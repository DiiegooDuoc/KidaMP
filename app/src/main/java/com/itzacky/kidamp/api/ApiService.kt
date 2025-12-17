package com.itzacky.kidamp.api

import com.itzacky.kidamp.model.Cancion
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {

    @GET("obtener_canciones.php")
    suspend fun getCanciones(): Response<List<Cancion>>

    @POST("crear_cancion.php")
    suspend fun crearCancion(@Body cancion: Cancion): Response<Unit>

    @HTTP(method = "DELETE", path = "eliminar_cancion.php", hasBody = true)
    suspend fun eliminarCancion(@Body cancion: Cancion): Response<Unit>

    @PUT("actualizar_cancion.php")
    suspend fun actualizarCancion(@Body cancion: Cancion): Response<Unit>
}
