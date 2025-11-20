package com.itzacky.kidamp.model

import androidx.room.*

@Database(entities = [Cancion::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun cancionDao(): CancionDao
}