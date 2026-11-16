package com.example.sjr_pringles.Project.Menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sjr_pringles.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private var hargaMakanan = 0
    private var hargaMinuman = 0

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

        // 1. Data Daftar Menu Kantin untuk RecyclerView
        val daftarMenu = listOf(
            KantinModel("Nasi Goreng Intel Core", "Makanan", 18000),
            KantinModel("Indomie Goreng Overclock Telur", "Makanan", 12000),
            KantinModel("Ayam Geprek RTX On", "Makanan", 20000),
            KantinModel("French Fries & Nugget", "Makanan", 15000),
            KantinModel("Es Kopi Susu Anti-Lag", "Minuman", 15000),
            KantinModel("Es Teh Manis Jumbo", "Minuman", 5000),
            KantinModel("Matcha Latte RGB", "Minuman", 17000)
        )

        // 2. Pasang CustomAdapter ke RecyclerView
        val adapter = KantinAdapter(daftarMenu) { menuDipilih ->
            if (menuDipilih.kategori == "Makanan") {
                binding.etNamaMakanan.setText(menuDipilih.namaMenu)
                hargaMakanan = menuDipilih.harga
            } else {
                binding.etNamaMinuman.setText(menuDipilih.namaMenu)
                hargaMinuman = menuDipilih.harga
            }
            updateTotalHarga()
            Toast.makeText(requireContext(), "${menuDipilih.namaMenu} dipilih!", Toast.LENGTH_SHORT).show()
        }

        binding.rvKantin.layoutManager = LinearLayoutManager(requireContext())
        binding.rvKantin.adapter = adapter

        // 3. Event Klik Tombol Kirim Pesanan (Kode Lama Kamu)
        binding.btnKirimPesanan.setOnClickListener {
            val makanan = binding.etNamaMakanan.text.toString().trim()
            val minuman = binding.etNamaMinuman.text.toString().trim()
            val catatan = binding.etCatatan.text.toString().trim()

            if (makanan.isEmpty() && minuman.isEmpty()) {
                Toast.makeText(requireContext(), "Pilih atau ketik makanan/minuman dulu!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val pesanKonfirmasi = if (catatan.isNotEmpty()) {
                "Pesanan ($makanan, $minuman) Terkirim! Catatan: $catatan"
            } else {
                "Pesanan ($makanan, $minuman) Berhasil Dikirim!"
            }

            Toast.makeText(requireContext(), pesanKonfirmasi, Toast.LENGTH_LONG).show()
        }
    }

    private fun updateTotalHarga() {
        val total = hargaMakanan + hargaMinuman
        binding.tvTotalPembayaran.text = "Rp $total"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}