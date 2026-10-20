package com.example.sjr_pringles

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sjr_pringles.Project.MainActivityProject
import com.example.sjr_pringles.Project.RegisterActivity // Import RegisterActivity dari sub-package Project
import com.example.sjr_pringles.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        // 1. Cek jika user sudah login, langsung lempar ke MainActivityProject
        if (sharedPref.getBoolean("isLogin", false)) {
            startActivity(Intent(this, MainActivityProject::class.java))
            finish()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2. Event Klik Tombol Login
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", username)
                editor.apply()

                // Notifikasi Login Berhasil
                Toast.makeText(this, "Selamat Datang, $username!", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, MainActivityProject::class.java))
                finish()
            } else {
                Toast.makeText(this, "Isi username dan password!", Toast.LENGTH_SHORT).show()
            }
        }

        // 3. TAMBAHAN: Event Klik Tombol/Teks Register untuk Pindah ke RegisterActivity
        binding.btnToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}