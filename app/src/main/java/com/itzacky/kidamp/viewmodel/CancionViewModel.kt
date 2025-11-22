package com.itzacky.kidamp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itzacky.kidamp.model.Cancion
import com.itzacky.kidamp.repository.CancionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CancionViewModel(private val repository: CancionRepository) : ViewModel() {

    // Expone el estado de la lista de canciones a la UI.
    private val _canciones = MutableStateFlow<List<Cancion>>(emptyList())
    val canciones: StateFlow<List<Cancion>> = _canciones.asStateFlow()

    init {
        // Se inicia la carga de canciones en cuanto el ViewModel es creado.
        cargarCanciones()
    }

    private fun cargarCanciones() {
        viewModelScope.launch {
            repository.getCancionesStream()
                .catch { e ->
                    // Opcional: Manejar errores, por si la llamada a la API falla.
                    // Por ahora, simplemente dejamos la lista vacía.
                    _canciones.value = emptyList()
                }
                .collect { listaDeCanciones ->
                    _canciones.value = listaDeCanciones
                }
        }
    }
}
