package com.example.sjr_pringles.Project

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sjr_pringles.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Event Klik Tombol Lanjut Verifikasi
        binding.btnRegister.setOnClickListener {
            val nama = binding.etNama.text.toString().trim()
            val noHp = binding.etNoHp.text.toString().trim()
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // 1. Validasi Input Form
            if (nama.isEmpty() || noHp.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2. Kirim data registrasi ke OtpActivity via Intent Extras
            val intent = Intent(this, OtpActivity::class.java).apply {
                putExtra("NAMA", nama)
                putExtra("NO_HP", noHp)
                putExtra("USERNAME", username)
                putExtra("PASSWORD", password)
            }
            startActivity(intent)
        }
    }
}