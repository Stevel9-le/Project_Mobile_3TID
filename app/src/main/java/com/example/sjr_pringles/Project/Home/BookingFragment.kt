package com.example.sjr_pringles.Project.Home

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sjr_pringles.Project.Network.BookingItem
import com.example.sjr_pringles.Project.Network.SupabaseClient
import com.example.sjr_pringles.databinding.FragmentBookingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingFragment : Fragment() {

    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!
    private var listBooking: List<BookingItem> = emptyList()
    private val hargaPerJam = 15000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hitung total harga otomatis saat durasi diubah
        binding.etDurasi.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val jam = s.toString().toIntOrNull() ?: 0
                binding.tvTotalHarga.text = "Rp ${jam * hargaPerJam}"
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // READ: Muat data booking dari Supabase
        loadBooking()

        // CREATE: Simpan booking baru
        binding.btnSubmitBooking.setOnClickListener {
            val nama = binding.etNamaPemesan.text.toString().trim()
            val pc = binding.etPcTarget.text.toString().trim()
            val durasi = binding.etDurasi.text.toString().toIntOrNull() ?: 0

            if (nama.isEmpty() || pc.isEmpty() || durasi <= 0) {
                Toast.makeText(requireContext(), "Lengkapi data booking dengan benar!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val total = durasi * hargaPerJam
            val item = BookingItem(
                nama_pemesan = nama,
                pc_target = pc,
                durasi_jam = durasi,
                total_harga = total
            )

            SupabaseClient.api.insertBooking(item).enqueue(object : Callback<List<BookingItem>> {
                override fun onResponse(call: Call<List<BookingItem>>, response: Response<List<BookingItem>>) {
                    if (response.isSuccessful) {
                        binding.etPcTarget.text?.clear()
                        Toast.makeText(requireContext(), "Booking $pc berhasil disimpan!", Toast.LENGTH_SHORT).show()
                        loadBooking()
                    }
                }

                override fun onFailure(call: Call<List<BookingItem>>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // UPDATE & DELETE
        binding.tvRiwayatBooking.setOnClickListener {
            if (listBooking.isEmpty()) return@setOnClickListener
            val pilihan = listBooking.map { "ID #${it.id} | ${it.nama_pemesan} - ${it.pc_target} (${it.durasi_jam} Jam)" }.toTypedArray()

            AlertDialog.Builder(requireContext())
                .setTitle("Pilih Booking (Edit / Hapus)")
                .setItems(pilihan) { _, which ->
                    tampilkanDialogAksi(listBooking[which])
                }
                .show()
        }
    }

    private fun loadBooking() {
        SupabaseClient.api.getBooking().enqueue(object : Callback<List<BookingItem>> {
            override fun onResponse(call: Call<List<BookingItem>>, response: Response<List<BookingItem>>) {
                if (response.isSuccessful && _binding != null) {
                    listBooking = response.body() ?: emptyList()
                    if (listBooking.isEmpty()) {
                        binding.tvRiwayatBooking.text = "Belum ada data booking di database."
                    } else {
                        val teks = listBooking.joinToString("\n\n") {
                            "[ID #${it.id}] ${it.nama_pemesan} | ${it.pc_target}\nDurasi: ${it.durasi_jam} Jam (Rp ${it.total_harga})"
                        }
                        binding.tvRiwayatBooking.text = "$teks\n\n(Ketuk kotak ini untuk Edit / Hapus)"
                    }
                }
            }

            override fun onFailure(call: Call<List<BookingItem>>, t: Throwable) {}
        })
    }

    private fun tampilkanDialogAksi(item: BookingItem) {
        AlertDialog.Builder(requireContext())
            .setTitle("Booking ${item.pc_target}")
            .setMessage("Pemesan: ${item.nama_pemesan} (${item.durasi_jam} Jam)")
            .setPositiveButton("Edit") { _, _ -> tampilkanDialogEdit(item) }
            .setNegativeButton("Hapus") { _, _ -> hapusBooking(item.id ?: return@setNegativeButton) }
            .setNeutralButton("Batal", null)
            .show()
    }

    private fun tampilkanDialogEdit(item: BookingItem) {
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 24, 48, 8)
        }
        val etNama = EditText(requireContext()).apply { setText(item.nama_pemesan) }
        val etPc = EditText(requireContext()).apply { setText(item.pc_target) }
        val etJam = EditText(requireContext()).apply { setText(item.durasi_jam.toString()) }
        layout.addView(etNama)
        layout.addView(etPc)
        layout.addView(etJam)

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Booking #${item.id}")
            .setView(layout)
            .setPositiveButton("Simpan") { _, _ ->
                val jamBaru = etJam.text.toString().toIntOrNull() ?: item.durasi_jam
                val updated = BookingItem(
                    nama_pemesan = etNama.text.toString(),
                    pc_target = etPc.text.toString(),
                    durasi_jam = jamBaru,
                    total_harga = jamBaru * hargaPerJam
                )
                SupabaseClient.api.updateBooking("eq.${item.id}", updated)
                    .enqueue(object : Callback<List<BookingItem>> {
                        override fun onResponse(call: Call<List<BookingItem>>, response: Response<List<BookingItem>>) {
                            Toast.makeText(requireContext(), "Booking diperbarui!", Toast.LENGTH_SHORT).show()
                            loadBooking()
                        }
                        override fun onFailure(call: Call<List<BookingItem>>, t: Throwable) {}
                    })
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun hapusBooking(id: Int) {
        SupabaseClient.api.deleteBooking("eq.$id").enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(requireContext(), "Booking dihapus!", Toast.LENGTH_SHORT).show()
                loadBooking()
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}