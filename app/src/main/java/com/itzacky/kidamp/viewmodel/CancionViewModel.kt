package com.itzacky.kidamp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itzacky.kidamp.model.Cancion
import com.itzacky.kidamp.repository.CancionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CancionViewModel(private val repository: CancionRepository) : ViewModel() {

    // --- ESTADO GENERAL ---
    private val _canciones = MutableStateFlow<List<Cancion>>(emptyList())
    val canciones = _canciones.asStateFlow()

    // --- ESTADO PARA EL FORMULARIO DE CREAR ---
    private val _nombreCancion = MutableStateFlow("")
    val nombreCancion = _nombreCancion.asStateFlow()

    private val _autorCancion = MutableStateFlow("")
    val autorCancion = _autorCancion.asStateFlow()

    private val _albumCancion = MutableStateFlow("")
    val albumCancion = _albumCancion.asStateFlow()

    // --- ESTADO PARA EL DIÁLOGO DE EDITAR ---
    private val _cancionSiendoEditada = MutableStateFlow<Cancion?>(null)
    val cancionSiendoEditada = _cancionSiendoEditada.asStateFlow()

    init {
        cargarCanciones()
    }

    // --- LÓGICA DE LECTURA (READ) ---
    fun cargarCanciones() {
        viewModelScope.launch {
            repository.getCancionesStream()
                .catch { _canciones.value = emptyList() }
                .collect { _canciones.value = it }
        }
    }

    // --- LÓGICA DE CREACIÓN (CREATE) ---
    fun onNombreChange(nombre: String) { _nombreCancion.value = nombre }
    fun onAutorChange(autor: String) { _autorCancion.value = autor }
    fun onAlbumChange(album: String) { _albumCancion.value = album }

    fun agregarCancion() {
        viewModelScope.launch {
            val nuevaCancion = Cancion(
                nombre_cancion = nombreCancion.value,
                autor_cancion = autorCancion.value,
                album_cancion = albumCancion.value
            )
            if (repository.crearCancion(nuevaCancion)) {
                cargarCanciones()
                limpiarCampos()
            }
        }
    }

    private fun limpiarCampos() {
        _nombreCancion.value = ""
        _autorCancion.value = ""
        _albumCancion.value = ""
    }

    // --- LÓGICA DE BORRADO (DELETE) ---
    fun eliminarCancion(cancion: Cancion) {
        viewModelScope.launch {
            if (repository.eliminarCancion(cancion)) {
                cargarCanciones()
            }
        }
    }

    // --- LÓGICA DE ACTUALIZACIÓN (UPDATE) ---
    fun onEditarClick(cancion: Cancion) {
        _cancionSiendoEditada.value = cancion
    }

    fun onEditarCancelar() {
        _cancionSiendoEditada.value = null
    }

    fun onEditarConfirmar(cancionActualizada: Cancion) {
        viewModelScope.launch {
            if (repository.actualizarCancion(cancionActualizada)) {
                cargarCanciones()
            }
            // Ocultamos el diálogo independientemente del resultado
            _cancionSiendoEditada.value = null
        }
    }
}
