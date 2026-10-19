package com.example.sjr_pringles.Project.Menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sjr_pringles.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Atur judul Toolbar
        binding.toolbar.title = "iCafe EDGE - Makanan & Minuman"

        // Event Klik Tombol Kirim Pesanan
        binding.btnKirimPesanan.setOnClickListener {
            val catatan = binding.etCatatan.text.toString().trim()
            if (catatan.isNotEmpty()) {
                Toast.makeText(requireContext(), "Pesanan Terkirim: $catatan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Pesanan Berhasil Dikirim!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}