package com.example.lab_week_10.viewmodels

// 1. Import LiveData dan MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TotalViewModel : ViewModel() {

    // 2. Buat versi private yang BISA DIUBAH (Mutable)
    private val _total = MutableLiveData<Int>()

    // 3. Buat versi public yang HANYA BISA DIBACA (Observable)
    val total: LiveData<Int>
        get() = _total // 'total' akan mengembalikan nilai dari '_total'

    // 4. Inisialisasi nilai awal
    init {
        _total.value = 0
    }

    // 5. Ubah fungsi increment untuk MENGUBAH NILAI .value
    fun incrementTotal() {
        // Ambil nilai saat ini (atau 0 jika null), tambahkan 1, lalu set lagi
        _total.value = (_total.value ?: 0) + 1
        // Fungsi ini tidak perlu me-return apa-apa
    }
}