package com.example.lab_week_10.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
// Anda perlu mengimpor kelas 'Total' yang Anda buat di file lain
import com.example.lab_week_10.database.Total

// Anotasi @Dao diletakkan langsung di atas interface.
// 'class TotalDao' yang membungkusnya sudah dihapus.
@Dao
interface TotalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(total: Total)

    @Update
    fun update(total: Total)

    @Delete
    fun delete(total: Total)

    @Query("SELECT * FROM total WHERE id = :id")
    fun getTotal(id: Long): List<Total>
}