// Pastikan file ini ada di: .../database/Total.kt
package com.example.lab_week_10.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

// Ini adalah kelas yang akan 'ditanam' (embedded)
data class TotalObject(
    @ColumnInfo(name = "value") val value: Int,
    @ColumnInfo(name = "date") val date: String
)

@Entity(tableName = "total")
data class Total(
    // ID diatur manual (sesuai MainActivity.ID = 1)
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,

    // Menanam TotalObject
    @Embedded
    val total: TotalObject
)