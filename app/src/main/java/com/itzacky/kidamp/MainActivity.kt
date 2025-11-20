package com.itzacky.kidamp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.itzacky.kidamp.ui.theme.KidaMPTheme
import kotlin.getValue
import androidx.room.*
import com.itzacky.kidamp.model.AppDatabase
import com.itzacky.kidamp.model.Cancion
import com.itzacky.kidamp.repository.CancionRepository
import com.itzacky.kidamp.ui.theme.Canciones
import com.itzacky.kidamp.viewmodel.CancionViewModel

class MainActivity : ComponentActivity() {


    private val db by lazy{
        Room.databaseBuilder(
            applicationContext,
            klass = AppDatabase::class.java,
            name = "SongsDB"
        ).build()
    }


    private val repository by lazy { CancionRepository(dao = db.cancionDao()) }

    private val viewModelCanciones by lazy { CancionViewModel(repository) }


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent{
            KidaMPTheme{
                Canciones (viewModel = viewModelCanciones)
            }
        }
    }


}