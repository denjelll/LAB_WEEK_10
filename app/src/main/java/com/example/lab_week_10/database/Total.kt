// Pakai package yang Anda tentukan
package com.example.lab_week_10.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// 1. Anotasi @Entity diletakkan langsung di atas data class
@Entity(tableName = "total")
// 2. Tidak perlu membungkusnya dengan "class Total" lain
data class Total(
    // 3. Hapus autoGenerate=true agar sesuai dengan MainActivity
    //    yang menggunakan ID = 1 secara manual.
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long, // Hapus nilai default "= 0"

    @ColumnInfo(name = "total")
    val total: Int
)