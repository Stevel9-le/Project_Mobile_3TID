package com.example.sjr_pringles.Project.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.sjr_pringles.databinding.FragmentQuickBillingBinding

class QuickBillingFragment : Fragment() {

    private var _binding: FragmentQuickBillingBinding? = null
    private val binding get() = _binding!!

    private var selectedHours = 1
    private val pricePerHour = 5000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuickBillingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title = "Top Up Billing iCafe"
        updateBillingCalculator()

        binding.btnTambahJam.setOnClickListener {
            selectedHours++
            updateBillingCalculator()
        }

        binding.btnKurangJam.setOnClickListener {
            if (selectedHours > 1) {
                selectedHours--
                updateBillingCalculator()
            } else {
                Toast.makeText(requireContext(), "Minimal 1 Jam Main!", Toast.LENGTH_SHORT).show()
            }
        }

        // KONFIRMASI TOP UP & KIRIM HASIL KE HOMEFRAGMENT
        binding.btnConfirmTopUp.setOnClickListener {
            val total = selectedHours * pricePerHour

            // Kirim jumlah jam ke HomeFragment
            setFragmentResult(
                "request_topup",
                bundleOf("added_hours" to selectedHours)
            )

            Toast.makeText(
                requireContext(),
                "Berhasil Top Up $selectedHours Jam (Total: Rp $total)!",
                Toast.LENGTH_SHORT
            ).show()

            // Kembali ke HomeFragment
            parentFragmentManager.popBackStack()
        }
    }

    private fun updateBillingCalculator() {
        val totalPrice = selectedHours * pricePerHour
        binding.tvJumlahJam.text = "$selectedHours Jam"
        binding.tvTotalHargaBilling.text = "Rp $totalPrice"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}