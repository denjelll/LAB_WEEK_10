// Pastikan file ini ada di: .../viewmodels/TotalViewModel.kt
package com.example.lab_week_10.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
// Impor TotalObject baru Anda
import com.example.lab_week_10.database.TotalObject
import java.util.Date

class TotalViewModel : ViewModel() {
    // LiveData sekarang menampung TotalObject
    private val _total = MutableLiveData<TotalObject>()
    val total: LiveData<TotalObject> = _total

    // setTotal sekarang menerima TotalObject
    fun setTotal(initialTotal: TotalObject) {
        _total.value = initialTotal
    }

    // incrementTotal HANYA memperbarui 'value'
    // dan membiarkan 'date' apa adanya
    fun incrementTotal() {
        val currentObject = _total.value
        if (currentObject != null) {
            // Salin objek, ganti 'value' saja
            _total.value = currentObject.copy(
                value = currentObject.value + 1
            )
        } else {
            // Fallback jika datanya null (seharusnya tidak terjadi)
            _total.value = TotalObject(value = 1, date = Date().toString())
        }
    }
}