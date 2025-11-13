package com.example.lab_week_10

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.lab_week_10.viewmodels.TotalViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewModel()
    }

    private fun updateText(total: Int) {
        view?.findViewById<TextView>(R.id.text_total)?.text =
            getString(R.string.text_total, total)
    }

    private fun prepareViewModel(){
        val viewModel =
            ViewModelProvider(requireActivity()).get(TotalViewModel::class.java)
        // Observe the LiveData object
        viewModel.total.observe(viewLifecycleOwner, { totalObject ->
            if (totalObject != null) {
                // ...
                // PERBAIKAN: Ambil .value dari objek tersebut
                updateText(totalObject.value) // BENAR: Mengirim Int
                // ...
            }
        })
    }

    // --- COMPANION OBJECT DIPINDAHKAN KE SINI ---
    // Ia harus berada di dalam class FirstFragment,
    // tapi di luar fungsi apa pun.
    companion object {
        /**
         * Gunakan factory method ini untuk membuat instance baru
         * dari fragment ini menggunakan parameter yang disediakan.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return Sebuah instance baru dari fragment FirstFragment.
         */
        // TODO: Ganti nama parameter jika perlu
        @JvmStatic // Praktik yang baik untuk factory method
        fun newInstance(param1: String, param2: String) =
            FirstFragment().apply { // Gunakan .apply untuk melampirkan arguments
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}