package com.example.sjr_pringles.Project.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sjr_pringles.Project.MainActivityProject
import com.example.sjr_pringles.R

class BookingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_booking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Kodingan logic UI / Inisialisasi komponen Booking kamu di sini
        // Catatan: Jika ada tombol kembali/selesai di layout booking, panggil fungsi tutupHalaman() di bawah
    }

    private fun tutupHalaman() {
        (activity as? MainActivityProject)?.tutupOnBoarding()
        parentFragmentManager.popBackStack()
    }
}