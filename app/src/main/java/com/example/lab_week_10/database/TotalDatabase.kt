package com.example.lab_week_10.database

import androidx.room.Database
import androidx.room.RoomDatabase
// Impor Dao dan Entity yang Anda buat sebelumnya
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDao

// Anotasi @Database diletakkan langsung di atas abstract class.
// 'class TotalDatabase' yang membungkusnya sudah dihapus.
@Database(entities = [Total::class], version = 1)
abstract class TotalDatabase : RoomDatabase() {
    // Deklarasikan Dao
    abstract fun totalDao(): TotalDao
}