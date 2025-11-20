package com.itzacky.kidamp.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CancionDao {
    /**
     * Esta es la función clave. Devuelve un Flow que emite la lista de canciones.
     * Room se encargará de que este Flow emita un nuevo valor cada vez
     * que la tabla "Songs" cambie (por un insert, update o delete).
     * He quitado el LIMIT 3 para que muestre todas las canciones.
     */
    @Query("SELECT * FROM Songs ORDER BY id DESC")
    fun getAll(): Flow<List<Cancion>>

    /**
     * Usa @Insert para agregar una canción. La anotación OnConflict le dice
     * a Room qué hacer si intentas insertar una canción que ya existe.
     * IGNORE simplemente no hará nada.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cancion: Cancion)

    /**
     * @Update para modificar una canción existente.
     */
    @Update
    suspend fun update(cancion: Cancion)

    /**
     * @Delete para borrar una canción.
     */
    @Delete
    suspend fun delete(cancion: Cancion)
}