package com.itzacky.kidamp.api

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    // La anotación @GET le dice a Retrofit que esto es una peticion GET.
    // El valor ("posts") es el "endpoint" o la parte final de la URL que queremos llamar.
    // URL Completa: https://jsonplaceholder.typicode.com/posts
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>
}

// Explicaciones:

// suspend: Marcamos la función como suspend para poder llamarla desde una corrutina sin bloquear el hilo principal.

// Response<List<Post>>: Le decimos a Retrofit que esperamos una lista de objetos Post y que la envuelva en un
// objeto Response. Esto nos permite comprobar si la llamada fue exitosa (código 200), si hubo un error (404, 500), etc.