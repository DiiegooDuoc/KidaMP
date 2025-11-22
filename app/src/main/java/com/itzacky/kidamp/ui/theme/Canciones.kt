package com.itzacky.kidamp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itzacky.kidamp.R
import com.itzacky.kidamp.model.Cancion
import com.itzacky.kidamp.viewmodel.CancionViewModel

@Composable
fun Canciones(viewModel: CancionViewModel) {

    // La lista de canciones que se obtiene desde la API
    val canciones by viewModel.canciones.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(52.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top // Pequeño ajuste para que el contenido se alinee arriba
    ) {

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
                .width(85.dp)
                .height(85.dp)
                .padding(bottom = 16.dp),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Tu Biblioteca",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        // La lista de canciones mantiene su diseño original
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(canciones) { cancion ->
                CancionCard(cancion = cancion) // Usamos un Composable separado para la tarjeta
            }
        }
    }
}

@Composable
fun CancionCard(cancion: Cancion) {
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
