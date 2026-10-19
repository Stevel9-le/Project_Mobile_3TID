package com.example.sjr_pringles.Project

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.sjr_pringles.LoginActivity
import com.example.sjr_pringles.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash) // Sesuaikan ID layout splash Anda

        // Tampil splash selama 2 detik lalu jalankan pengecekan sesi login
        Handler(Looper.getMainLooper()).postDelayed({
            checkLoginSession()
        }, 2000)
    }

    private fun checkLoginSession() {
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLogin", false)

        if (isLoggedIn) {
            // Jika user sudah login -> langsung ke MainActivityProject
            startActivity(Intent(this, MainActivityProject::class.java))
        } else {
            // Jika user belum login -> arahkan ke LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish() // Tutup SplashActivity agar tidak bisa dikembalikan via tombol Back
    }
}