package com.example.sjr_pringles.Project.Home

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.sjr_pringles.Project.Menu.MenuFragment
import com.example.sjr_pringles.Project.WebViewActivity
import com.example.sjr_pringles.R
import com.example.sjr_pringles.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var countDownTimer: CountDownTimer? = null
    private val CHANNEL_ID = "icafe_billing_channel"

    // Simpan sisa waktu billing dalam milidetik
    private var remainingTimeMs: Long = 10 * 60 * 1000 // Awal 10 Menit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title = "iCafe Edge - Home"

        createNotificationChannel()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission()
        }

        // Mulai timer awal
        startBillingTimer(remainingTimeMs)

        // LISTEN HASIL TOP UP DARI QUICKBILLINGFRAGMENT
        setFragmentResultListener("request_topup") { _, bundle ->
            val addedHours = bundle.getInt("added_hours", 0)
            if (addedHours > 0) {
                // Tambahkan waktu tambahan (dalam milidetik)
                val addedMs = addedHours * 3600 * 1000L
                remainingTimeMs += addedMs

                // Restart timer dengan waktu baru
                countDownTimer?.cancel()
                startBillingTimer(remainingTimeMs)

                Toast.makeText(requireContext(), "Waktu billing berhasil ditambah!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBooking.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BookingFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnKantin.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MenuFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnWebView.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java))
        }

        binding.tvBillingTimer.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, QuickBillingFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun startBillingTimer(durationInMillis: Long) {
        countDownTimer = object : CountDownTimer(durationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeMs = millisUntilFinished // Update sisa waktu berjalan
                val hours = (millisUntilFinished / 1000) / 3600
                val minutes = ((millisUntilFinished / 1000) % 3600) / 60
                val seconds = (millisUntilFinished / 1000) % 60

                _binding?.tvBillingTimer?.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)

                if (minutes == 5L && seconds == 0L) {
                    sendBillingWarningNotification()
                }
            }

            override fun onFinish() {
                remainingTimeMs = 0
                _binding?.tvBillingTimer?.text = "00:00:00"
                context?.let {
                    Toast.makeText(it, "Sesi Billing iCafe Kamu Telah Habis!", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "iCafe Billing Notification"
            val descriptionText = "Notifikasi Peringatan Sisa Waktu Billing Warnet"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @Suppress("MissingPermission")
    private fun sendBillingWarningNotification() {
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("Edge iCafe Warning")
            .setContentText("Sisa waktu billing kamu tinggal 5 menit lagi!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(requireContext())) {
            notify(1001, builder.build())
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
        _binding = null
    }
}