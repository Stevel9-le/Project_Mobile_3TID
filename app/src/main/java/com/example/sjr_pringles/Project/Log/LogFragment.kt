package com.example.sjr_pringles.Project.Log

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sjr_pringles.databinding.FragmentLogBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LogFragment : Fragment() {

    private var _binding: FragmentLogBinding? = null
    private val binding get() = _binding!!

    private val daftarKeluhan = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Logbook Keluhan"
        }

        binding.btnKirimKeluhan.setOnClickListener {
            val nomorPc = binding.etNomorPC.text.toString().trim()
            val isiKeluhan = binding.etKeluhan.text.toString().trim()

            if (nomorPc.isEmpty() || isiKeluhan.isEmpty()) {
                Toast.makeText(requireContext(), "Nomor PC dan keluhan wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val waktu = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            val logBaru = "[$waktu] PC $nomorPc: $isiKeluhan\nStatus: Terkirim ke Operator"
            daftarKeluhan.add(0, logBaru)

            binding.tvRiwayatKeluhan.text = daftarKeluhan.joinToString("\n\n")

            binding.etNomorPC.text?.clear()
            binding.etKeluhan.text?.clear()

            Toast.makeText(requireContext(), "Keluhan berhasil dikirim ke operator!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}