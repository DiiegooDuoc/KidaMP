package com.itzacky.kidamp.ui.theme

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itzacky.kidamp.R
import com.itzacky.kidamp.model.Cancion
import com.itzacky.kidamp.viewmodel.CancionViewModel

val limiteMaximoNombre = 50
val limiteMaximoArtista = 30
val limiteMaximoAlbum = 40

@Composable
fun Canciones(viewModel: CancionViewModel) {

    // Estados para los campos del formulario de agregar
    val nombre by viewModel.nombreCancion.collectAsState()
    val artista by viewModel.autorCancion.collectAsState()
    val album by viewModel.albumCancion.collectAsState()

    // Estado para la lista de canciones
    val canciones by viewModel.canciones.collectAsState()

    // Estado para saber qué canción se está editando
    val cancionParaEditar by viewModel.cancionSiendoEditada.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // --- FORMULARIO DE AGREGAR CANCIÓN (se mantiene igual) ---
        // ...

        Text(
            text = "Recopilador de Música",
            color = Color.White,
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 14.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.music),
            contentDescription = "Musica",
            modifier = Modifier
                .size(85.dp)
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { if (it.length <= limiteMaximoNombre) viewModel.onNombreChange(it) },
            label = { Text("Nombre de la Canción") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White),
            singleLine = true,
            trailingIcon = { Text("${nombre.length} / $limiteMaximoNombre", modifier = Modifier.padding(end = 12.dp), color = Color.Gray) }
        )

        OutlinedTextField(
            value = artista,
            onValueChange = { if (it.length <= limiteMaximoArtista) viewModel.onAutorChange(it) },
            label = { Text("Nombre del Artista") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White),
            singleLine = true,
            trailingIcon = { Text("${artista.length} / $limiteMaximoArtista", modifier = Modifier.padding(end = 12.dp), color = Color.Gray) }
        )

        OutlinedTextField(
            value = album,
            onValueChange = { if (it.length <= limiteMaximoAlbum) viewModel.onAlbumChange(it) },
            label = { Text("Nombre del Album") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White),
            singleLine = true,
            trailingIcon = { Text("${album.length} / $limiteMaximoAlbum", modifier = Modifier.padding(end = 12.dp), color = Color.Gray) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        val context = LocalContext.current
        Button(
            onClick = {
                if (nombre.isNotBlank() && artista.isNotBlank() && album.isNotBlank()) {
                    viewModel.agregarCancion()
                    Toast.makeText(context, "Canción agregada", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Agregar Canción")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Tu Biblioteca",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        // --- LISTA DE CANCIONES ---
        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
            items(canciones) { cancion ->
                CancionCard(
                    cancion = cancion,
                    onEliminarClick = { viewModel.eliminarCancion(cancion) },
                    onEditarClick = { viewModel.onEditarClick(cancion) } // Conectamos el click de editar
                )
            }
        }
    }

    // --- DIÁLOGO DE EDICIÓN ---
    // Se mostrará solo si hay una canción seleccionada para editar
    cancionParaEditar?.let { cancion ->
        EditarCancionDialog(
            cancion = cancion,
            onConfirm = { cancionActualizada ->
                viewModel.onEditarConfirmar(cancionActualizada)
            },
            onDismiss = {
                viewModel.onEditarCancelar()
            }
        )
    }
}

@Composable
fun CancionCard(cancion: Cancion, onEliminarClick: () -> Unit, onEditarClick: () -> Unit) {
    var menuVisible by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Columna con la información de la canción
            Column(modifier = Modifier.weight(1f).padding(vertical = 12.dp)) {
                Text(text = cancion.nombre_cancion, style = MaterialTheme.typography.titleMedium, color = Color.White)
                Text(text = "Artista: ${cancion.autor_cancion}", style = MaterialTheme.typography.bodySmall, color = Color.LightGray)
                Text(text = "Álbum: ${cancion.album_cancion}", style = MaterialTheme.typography.bodySmall, color = Color.LightGray)
            }
            // Caja para el icono del menú
            Box {
                IconButton(onClick = { menuVisible = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Más opciones", tint = Color.White)
                }
                // Menú desplegable
                DropdownMenu(
                    expanded = menuVisible,
                    onDismissRequest = { menuVisible = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Eliminar") },
                        onClick = {
                            menuVisible = false
                            onEliminarClick()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Editar") },
                        onClick = {
                            menuVisible = false
                            onEditarClick() // Llamamos a la nueva función
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EditarCancionDialog(
    cancion: Cancion,
    onConfirm: (Cancion) -> Unit,
    onDismiss: () -> Unit
) {
    // Estados internos para los campos de texto del diálogo
    var nombre by remember { mutableStateOf(cancion.nombre_cancion) }
    var autor by remember { mutableStateOf(cancion.autor_cancion) }
    var album by remember { mutableStateOf(cancion.album_cancion) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Canción") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre de la canción") }
                )
                OutlinedTextField(
                    value = autor,
                    onValueChange = { autor = it },
                    label = { Text("Artista") }
                )
                OutlinedTextField(
                    value = album,
                    onValueChange = { album = it },
                    label = { Text("Álbum") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val cancionActualizada = cancion.copy(
                        nombre_cancion = nombre,
                        autor_cancion = autor,
                        album_cancion = album
                    )
                    onConfirm(cancionActualizada)
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
