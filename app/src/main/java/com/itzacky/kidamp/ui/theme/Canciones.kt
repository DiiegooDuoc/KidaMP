package com.itzacky.kidamp.ui.theme

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.itzacky.kidamp.R
import com.itzacky.kidamp.viewmodel.CancionViewModel

@Composable
fun Canciones(viewModel: CancionViewModel) {

    val nombre by viewModel.nombre_cancion.collectAsState()
    val artista by viewModel.autor_cancion.collectAsState()
    val album by viewModel.album_cancion.collectAsState()
    // La lista de canciones
    val canciones by viewModel.canciones.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(52.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // =======================================================
        // --- 2. SECCIÓN DEL FORMULARIO (PARA AÑADIR CANCIONES) ---
        // =======================================================


        Image(
            painter = painterResource(id = R.drawable.music),
            contentDescription = "Musica",
            modifier = Modifier
                .width(50.dp) // mas chico pa que de espacio a la lista
                .height(50.dp)
        )
        Text(
            text = "Recopilador de Música",
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 14.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { viewModel.onNombreChange(it) },
            label = { Text("Nombre de la Canción") },
            modifier = Modifier.fillMaxWidth() // Ocupa todo el ancho
        )

        OutlinedTextField(
            value = artista,
            onValueChange = { viewModel.onArtistaChange(it) },
            label = { Text("Nombre del Artista") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = album,
            onValueChange = { viewModel.onAlbumChange(it) },
            label = { Text("Nombre del Album") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        val context = LocalContext.current

        Button(
            onClick = {
                if (nombre.isNotBlank() && artista.isNotBlank() && album.isNotBlank()) {
                    viewModel.agregarCancion()
                    Toast.makeText(context, "Canción agregada", Toast.LENGTH_SHORT).show()
                    viewModel.limpiarCampos()
                } else {
                    Toast.makeText(context, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Agregar Canción")
        }

        // =======================================================
        // --- 3. SECCIÓN DE LA LISTA (PARA MOSTRAR CANCIONES) ---
        // =======================================================

        Spacer(modifier = Modifier.height(24.dp)) // Un espacio más grande para separar

        Text(
            text = "Tu Biblioteca",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(canciones) { cancion ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = cancion.nombre_cancion,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                        Text(
                            text = "Artista: ${cancion.autor_cancion}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.LightGray
                        )
                        Text(
                            text = "Álbum: ${cancion.album_cancion}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.LightGray
                        )
                    }
                }
            }
        }
    }
}

