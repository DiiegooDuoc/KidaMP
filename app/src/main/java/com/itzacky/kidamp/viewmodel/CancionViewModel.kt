package com.itzacky.kidamp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itzacky.kidamp.model.Cancion
import com.itzacky.kidamp.repository.CancionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CancionViewModel(private val repository: CancionRepository) : ViewModel() {

    // --- CAMPOS DE TEXTO OBSERVABLES POR LA VISTA ---
    // Hacemos uno privado (mutable) y uno público (inmutable) para proteger los datos.
    private val _nombre_cancion = MutableStateFlow("")
    val nombre_cancion = _nombre_cancion.asStateFlow()

    private val _autor_cancion = MutableStateFlow("")
    val autor_cancion = _autor_cancion.asStateFlow()

    private val _album_cancion = MutableStateFlow("")
    val album_cancion = _album_cancion.asStateFlow()


    // --- LISTA DE CANCIONES OBSERVABLE POR LA VISTA ---
    private val _canciones = MutableStateFlow<List<Cancion>>(emptyList())
    val canciones = _canciones.asStateFlow()

    init {
        cargarCanciones()
    }

    private fun cargarCanciones() {
        viewModelScope.launch {
            repository.getAll().collect { listaDeCanciones ->
                _canciones.value = listaDeCanciones
            }
        }
    }

    // --- FUNCIONES AÑADIDAS PARA LA VISTA (Canciones.kt) ---

    /**
     * Actualiza el valor del nombre de la canción cada vez que el usuario escribe.
     * Llamada desde el `onValueChange` del TextField.
     */
    fun onNombreChange(nombre: String) {
        _nombre_cancion.value = nombre
    }

    /**
     * Actualiza el valor del autor de la canción.
     */
    fun onArtistaChange(artista: String) {
        _autor_cancion.value = artista
    }

    /**
     * Actualiza el valor del álbum de la canción.
     */
    fun onAlbumChange(album: String) {
        _album_cancion.value = album
    }

    /**
     * Agrega la canción a la base de datos.
     * Toma los valores actuales de los StateFlows.
     * No necesita parámetros.
     */
    fun agregarCancion() {
        viewModelScope.launch {
            // Construimos el objeto Cancion con los valores actuales
            val nuevaCancion = Cancion(
                nombre_cancion = _nombre_cancion.value, // <--- CORREGIDO
                autor_cancion = _autor_cancion.value,  // <--- CORREGIDO
                album_cancion = _album_cancion.value   // <--- CORREGIDO
            )
            // Llamamos al repositorio para que la inserte
            repository.insert(nuevaCancion)
            // La lista en la UI se actualizará sola gracias al Flow.
        }
    }

    /**
     * Limpia los campos de texto después de agregar una canción.
     * Llamada desde el `onClick` del botón en Canciones.kt.
     */
    fun limpiarCampos() {
        _nombre_cancion.value = ""
        _autor_cancion.value = ""
        _album_cancion.value = ""
    }

    fun eliminarCancion(cancion: Cancion) {
        viewModelScope.launch {
            repository.delete(cancion)
        }
    }
}

