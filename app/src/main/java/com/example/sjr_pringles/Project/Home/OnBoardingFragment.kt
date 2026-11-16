package com.example.sjr_pringles.Project.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.sjr_pringles.Project.MainActivityProject
import com.example.sjr_pringles.R

class OnBoardingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPagerOnBoard)
        val tvIndicator = view.findViewById<TextView>(R.id.tvStepIndicator)
        val btnNext = view.findViewById<Button>(R.id.btnNextOnBoard)

        val slides = listOf(
            OnBoardItem(
                "Selamat Datang di Edge iCafe",
                "Pantau sisa waktu billing bermainmu secara real-time lengkap dengan notifikasi pengingat.",
                android.R.drawable.ic_dialog_info
            ),
            OnBoardItem(
                "Top Up Billing Tanpa Antre",
                "Tambah durasi jam main kapan saja lewat kalkulator Quick Billing tanpa perlu ke kasir.",
                android.R.drawable.ic_input_add
            ),
            OnBoardItem(
                "Pesan Makanan Langsung ke Meja",
                "Lapar saat push rank? Pesan menu kantin favoritmu langsung dari aplikasi.",
                android.R.drawable.ic_menu_sort_by_size
            )
        )

        viewPager.adapter = OnBoardingAdapter(slides)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tvIndicator.text = "${position + 1} / 3"
                btnNext.text = if (position == 2) "Mulai Sekarang" else "Lanjut"
            }
        })

        btnNext.setOnClickListener {
            if (viewPager.currentItem < 2) {
                viewPager.currentItem += 1
            } else {
                (activity as? MainActivityProject)?.tutupOnBoarding()
            }
        }
    }
}