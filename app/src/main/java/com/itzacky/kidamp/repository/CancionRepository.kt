package com.itzacky.kidamp.repository

import com.itzacky.kidamp.api.RetrofitClient
import com.itzacky.kidamp.model.Cancion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CancionRepository {

    /**
     * Obtiene las canciones desde la API y las emite como un Flow.
     * Esto permite que el ViewModel observe los datos de forma reactiva,
     * manteniendo un patrón de diseño consistente.
     */
    fun getCancionesStream(): Flow<List<Cancion>> = flow {
        try {
            val response = RetrofitClient.apiService.getCanciones()
            if (response.isSuccessful) {
                // Si la respuesta es exitosa, emitimos la lista de canciones.
                // Si el body es null, emitimos una lista vacía.
                emit(response.body() ?: emptyList())
            } else {
                // En caso de error en la respuesta (ej: 404, 500), emitimos una lista vacía.
                emit(emptyList())
            }
        } catch (e: Exception) {
            // En caso de una excepción (ej: sin conexión a internet), emitimos una lista vacía.
            emit(emptyList())
        }
    }
}
