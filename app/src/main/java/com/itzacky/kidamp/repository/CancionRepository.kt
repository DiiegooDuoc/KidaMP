package com.itzacky.kidamp.repository

import com.itzacky.kidamp.api.RetrofitClient
import com.itzacky.kidamp.model.Cancion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CancionRepository {

    fun getCancionesStream(): Flow<List<Cancion>> = flow {
        try {
            val response = RetrofitClient.apiService.getCanciones()
            if (response.isSuccessful) {
                emit(response.body() ?: emptyList())
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    suspend fun crearCancion(cancion: Cancion): Boolean {
        return try {
            val response = RetrofitClient.apiService.crearCancion(cancion)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun eliminarCancion(cancion: Cancion): Boolean {
        return try {
            val response = RetrofitClient.apiService.eliminarCancion(cancion)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Llama a la API para actualizar una canción existente en el servidor.
     * @param cancion El objeto Cancion con los datos actualizados.
     * @return Devuelve true si la actualización fue exitosa, false en caso contrario.
     */
    suspend fun actualizarCancion(cancion: Cancion): Boolean {
        return try {
            val response = RetrofitClient.apiService.actualizarCancion(cancion)
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }
}
