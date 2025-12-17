package com.itzacky.kidamp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Cliente que consume el Servicio del API (b.d externa)
object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2/api/"

    // Creamos un interceptor para loggear las llamadas y respuestas de la red.
    // Esto es muy útil para depurar.
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Creamos un cliente de OkHttp y le añadimos el interceptor.
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            // Añadimos el cliente OkHttp a Retrofit.
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
