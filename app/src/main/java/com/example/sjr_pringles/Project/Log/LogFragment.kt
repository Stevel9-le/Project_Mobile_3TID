package com.example.sjr_pringles.Project.Log

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sjr_pringles.Project.Network.KeluhanItem
import com.example.sjr_pringles.Project.Network.SupabaseClient
import com.example.sjr_pringles.databinding.FragmentLogBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogFragment : Fragment() {

    private var _binding: FragmentLogBinding? = null
    private val binding get() = _binding!!
    private var listKeluhan: List<KeluhanItem> = emptyList()

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
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "Logbook Keluhan"

        loadKeluhan()

        // CREATE
        binding.btnKirimKeluhan.setOnClickListener {
            val nomorPc = binding.etNomorPC.text.toString().trim()
            val isiKeluhan = binding.etKeluhan.text.toString().trim()

            if (nomorPc.isEmpty() || isiKeluhan.isEmpty()) {
                Toast.makeText(requireContext(), "Nomor PC dan keluhan wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val item = KeluhanItem(nomor_pc = nomorPc, isi_keluhan = isiKeluhan)
            SupabaseClient.api.insertKeluhan(item).enqueue(object : Callback<List<KeluhanItem>> {
                override fun onResponse(call: Call<List<KeluhanItem>>, response: Response<List<KeluhanItem>>) {
                    if (response.isSuccessful) {
                        binding.etNomorPC.text?.clear()
                        binding.etKeluhan.text?.clear()
                        Toast.makeText(requireContext(), "Keluhan berhasil dikirim ke Supabase!", Toast.LENGTH_SHORT).show()
                        loadKeluhan()
                    }
                }

                override fun onFailure(call: Call<List<KeluhanItem>>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // UPDATE & DELETE
        binding.tvRiwayatKeluhan.setOnClickListener {
            if (listKeluhan.isEmpty()) return@setOnClickListener
            val pilihan = listKeluhan.map { "ID #${it.id} | PC ${it.nomor_pc}: ${it.isi_keluhan}" }.toTypedArray()

            AlertDialog.Builder(requireContext())
                .setTitle("Pilih Keluhan (Edit / Hapus)")
                .setItems(pilihan) { _, which ->
                    tampilkanDialogAksi(listKeluhan[which])
                }
                .show()
        }
    }

    // READ
    private fun loadKeluhan() {
        SupabaseClient.api.getKeluhan().enqueue(object : Callback<List<KeluhanItem>> {
            override fun onResponse(call: Call<List<KeluhanItem>>, response: Response<List<KeluhanItem>>) {
                if (response.isSuccessful && _binding != null) {
                    listKeluhan = response.body() ?: emptyList()
                    if (listKeluhan.isEmpty()) {
                        binding.tvRiwayatKeluhan.text = "Belum ada keluhan yang dikirim."
                    } else {
                        val teks = listKeluhan.joinToString("\n\n") {
                            "[ID #${it.id}] PC ${it.nomor_pc} - ${it.isi_keluhan}\nStatus: ${it.status}"
                        }
                        binding.tvRiwayatKeluhan.text = "$teks\n\n(Ketuk kotak ini untuk Edit / Hapus)"
                    }
                }
            }

            override fun onFailure(call: Call<List<KeluhanItem>>, t: Throwable) {}
        })
    }

    private fun tampilkanDialogAksi(item: KeluhanItem) {
        AlertDialog.Builder(requireContext())
            .setTitle("Keluhan PC ${item.nomor_pc}")
            .setMessage(item.isi_keluhan)
            .setPositiveButton("Edit") { _, _ -> tampilkanDialogEdit(item) }
            .setNegativeButton("Hapus") { _, _ -> hapusKeluhan(item.id ?: return@setNegativeButton) }
            .setNeutralButton("Batal", null)
            .show()
    }

    private fun tampilkanDialogEdit(item: KeluhanItem) {
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 24, 48, 8)
        }
        val etPc = EditText(requireContext()).apply { setText(item.nomor_pc) }
        val etIsi = EditText(requireContext()).apply { setText(item.isi_keluhan) }
        layout.addView(etPc)
        layout.addView(etIsi)

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Keluhan #${item.id}")
            .setView(layout)
            .setPositiveButton("Simpan") { _, _ ->
                val updated = KeluhanItem(
                    nomor_pc = etPc.text.toString(),
                    isi_keluhan = etIsi.text.toString(),
                    status = "Diperbarui"
                )
                SupabaseClient.api.updateKeluhan("eq.${item.id}", updated)
                    .enqueue(object : Callback<List<KeluhanItem>> {
                        override fun onResponse(call: Call<List<KeluhanItem>>, response: Response<List<KeluhanItem>>) {
                            Toast.makeText(requireContext(), "Data diperbarui!", Toast.LENGTH_SHORT).show()
                            loadKeluhan()
                        }
                        override fun onFailure(call: Call<List<KeluhanItem>>, t: Throwable) {}
                    })
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun hapusKeluhan(id: Int) {
        SupabaseClient.api.deleteKeluhan("eq.$id").enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(requireContext(), "Keluhan dihapus!", Toast.LENGTH_SHORT).show()
                loadKeluhan()
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}