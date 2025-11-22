package com.itzacky.kidamp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.itzacky.kidamp.repository.CancionRepository
import com.itzacky.kidamp.ui.theme.Canciones
import com.itzacky.kidamp.ui.theme.KidaMPTheme
import com.itzacky.kidamp.viewmodel.CancionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KidaMPTheme {
                // Instanciamos el repositorio directamente. Ya no depende de la base de datos.
                val repository = CancionRepository()

                // Usamos el 'viewModel' composable para crear/obtener el ViewModel.
                // Le pasamos una factory simple para que sepa cómo construir el ViewModel con nuestro repositorio.
                val viewModel: CancionViewModel = viewModel(
                    factory = object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            if (modelClass.isAssignableFrom(CancionViewModel::class.java)) {
                                @Suppress("UNCHECKED_CAST")
                                return CancionViewModel(repository) as T
                            }
                            throw IllegalArgumentException("Unknown ViewModel class")
                        }
                    }
                )

                // Pasamos el ViewModel a nuestro Composable de Canciones
                Canciones(viewModel = viewModel)
            }
        }
    }
}
