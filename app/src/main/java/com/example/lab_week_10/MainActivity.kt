package com.example.lab_week_10

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast // <<< TAMBAHAN: Untuk menampilkan Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.lab_week_10.database.Total // <<< TAMBAHAN
import com.example.lab_week_10.database.TotalDatabase
import com.example.lab_week_10.database.TotalObject // <<< TAMBAHAN
import com.example.lab_week_10.viewmodels.TotalViewModel
import java.util.Date // <<< TAMBAHAN: Untuk mengambil tanggal

class MainActivity : AppCompatActivity() {
    private val db by lazy { prepareDatabase() }
    private val viewModel by lazy {
        ViewModelProvider(this)[TotalViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Memuat data dari DB saat aplikasi dibuat
        initializeValueFromDatabase()
        // Menyiapkan observer dan listener
        prepareViewModel()
    }

    // <<< BARU: Tampilkan Toast saat aplikasi dimulai (sesuai tugas)
    override fun onStart() {
        super.onStart()
        // Ambil data tanggal yang sudah dimuat ke ViewModel oleh onCreate
        val lastDate = viewModel.total.value?.date ?: "Belum ada data"
        Toast.makeText(
            this,
            "Aplikasi terakhir dibuka: $lastDate",
            Toast.LENGTH_LONG
        ).show()
    }

    // <<< BARU: Simpan tanggal baru saat aplikasi dijeda (sesuai tugas)
    override fun onPause() {
        super.onPause()
        // 1. Dapatkan nilai 'value' saat ini dari ViewModel
        val currentValue = viewModel.total.value?.value ?: 0
        // 2. Buat tanggal baru
        val newDate = Date().toString()

        // 3. Buat objek TotalObject baru
        val newTotalObject = TotalObject(value = currentValue, date = newDate)

        // 4. Perbarui ViewModel agar UI konsisten saat kembali
        viewModel.setTotal(newTotalObject)

        // 5. Simpan ke database di background thread
        Thread {
            db.totalDao().update(Total(id = ID, total = newTotalObject))
        }.start()
    }

    // <<< DIMODIFIKASI: updateText sekarang menerima 'value' (Int)
    private fun updateText(totalValue: Int) {
        findViewById<TextView>(R.id.text_total).text =
            getString(R.string.text_total, totalValue)
    }

    // <<< DIMODIFIKASI: Observer sekarang menerima TotalObject
    private fun prepareViewModel(){
        // viewModel.total.observe sekarang mengembalikan TotalObject
        viewModel.total.observe(this, { totalObject ->
            // Kirim HANYA 'value'-nya ke updateText
            if (totalObject != null) {
                updateText(totalObject.value)
            }
        })

        findViewById<Button>(R.id.button_increment).setOnClickListener {
            viewModel.incrementTotal() // Minta VM untuk +1

            // Ambil TotalObject yang baru (dengan value +1) dari VM
            val newTotalObject = viewModel.total.value
            if (newTotalObject != null) {
                // Simpan nilai BARU ini ke database di background thread
                Thread {
                    db.totalDao().update(Total(id = ID, total = newTotalObject))
                }.start()
            }
        }
    }

    private fun prepareDatabase(): TotalDatabase {
        return Room.databaseBuilder(
            applicationContext,
            TotalDatabase::class.java, "total-database"
        ).allowMainThreadQueries().build()
    }

    // <<< DIMODIFIKASI: Inisialisasi sekarang menggunakan TotalObject
    private fun initializeValueFromDatabase() {
        val totalList = db.totalDao().getTotal(ID)

        if (totalList.isEmpty()) {
            // Jika DB kosong, buat data awal
            val initialDate = Date().toString()
            val initialObject = TotalObject(value = 0, date = initialDate)

            // Masukkan ke DB
            db.totalDao().insert(Total(id = ID, total = initialObject))

            // Set ViewModel
            viewModel.setTotal(initialObject)
        } else {
            // Jika DB ada, pakai data dari DB (yaitu .total dari totalList)
            viewModel.setTotal(totalList.first().total)
        }
    }

    companion object {
        const val ID: Long = 1
    }
}