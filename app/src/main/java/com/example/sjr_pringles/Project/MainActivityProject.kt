package com.example.sjr_pringles.Project

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sjr_pringles.Project.Home.HomeFragment
import com.example.sjr_pringles.Project.Log.LogFragment
import com.example.sjr_pringles.Project.Menu.MenuFragment
import com.example.sjr_pringles.Project.Profile.ProfileFragment
import com.example.sjr_pringles.R
import com.example.sjr_pringles.databinding.ActivityMainProjectBinding

class MainActivityProject : AppCompatActivity() {

    private lateinit var binding: ActivityMainProjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hilangkan tint agar warna asli ikon keluar
        binding.bottomNavigation.itemIconTintList = null

        // Set HomeFragment sebagai fragment default saat pertama kali aplikasi dibuka
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // Setup Listener Bottom Navigation dengan Notifikasi Toast
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    Toast.makeText(this, "Membuka Menu Home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_menu -> {
                    replaceFragment(MenuFragment())
                    Toast.makeText(this, "Membuka Menu Makanan & Minuman", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_log -> {
                    replaceFragment(LogFragment())
                    Toast.makeText(this, "Membuka Logbook Keluhan", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(ProfileFragment())
                    Toast.makeText(this, "Membuka Profil", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    // Fungsi untuk mengganti Fragment di dalam FrameLayout container
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}