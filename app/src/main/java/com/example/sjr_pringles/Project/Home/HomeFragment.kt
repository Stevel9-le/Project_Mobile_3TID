package com.example.sjr_pringles.Project.Home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sjr_pringles.Project.Menu.MenuFragment
import com.example.sjr_pringles.Project.WebViewActivity
import com.example.sjr_pringles.R
import com.example.sjr_pringles.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Atur judul Toolbar
        binding.toolbar.title = "iCafe Edge - Home"

        // 1. Tombol Booking PC -> Buka BookingFragment
        binding.btnBooking.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BookingFragment())
                .addToBackStack(null)
                .commit()
        }

        // 2. Tombol Makanan dan Minuman -> Buka MenuFragment
        binding.btnKantin.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MenuFragment())
                .addToBackStack(null)
                .commit()
        }

        // 3. Tombol Portal Member -> Buka WebViewActivity
        binding.btnWebView.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}