package com.itzacky.kidamp.repository

import com.itzacky.kidamp.model.Cancion
import com.itzacky.kidamp.model.CancionDao
import kotlinx.coroutines.flow.Flow

class CancionRepository(private val dao: CancionDao) {

    /**
     * Esta función ya no es 'suspend'. Simplemente devuelve el Flow
     * que recibe del DAO para que el ViewModel pueda observarlo.
     */
    fun getAll(): Flow<List<Cancion>> = dao.getAll()

    /**
     * Las funciones de un solo disparo (insert, update, delete) siguen
     * siendo 'suspend' porque realizan una operación y terminan.
     */
    suspend fun insert(cancion: Cancion) {
        dao.insert(cancion)
    }

    suspend fun update(cancion: Cancion) {
        dao.update(cancion)
    }

    suspend fun delete(cancion: Cancion) {
        dao.delete(cancion)
    }
}
