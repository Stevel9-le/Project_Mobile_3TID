package com.example.sjr_pringles.Project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sjr_pringles.LoginActivity // 1. Perbaikan: Tambah import LoginActivity
import com.example.sjr_pringles.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class OtpActivity : AppCompatActivity() {

    private lateinit var etOtpCode: TextInputEditText
    private lateinit var btnVerifyOtp: MaterialButton

    private var nama: String? = null
    private var noHp: String? = null
    private var username: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        etOtpCode = findViewById(R.id.etOtpCode)
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp)

        nama = intent.getStringExtra("NAMA")
        noHp = intent.getStringExtra("NO_HP")
        username = intent.getStringExtra("USERNAME")
        password = intent.getStringExtra("PASSWORD")

        btnVerifyOtp.setOnClickListener {
            val inputOtp = etOtpCode.text.toString().trim()

            if (inputOtp.isEmpty() || inputOtp != noHp) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Verifikasi Gagal")
                    .setMessage("Kode OTP salah atau kosong!")
                    .setPositiveButton("Coba Lagi", null)
                    .show()
            } else {
                saveToSharedPreferences()

                MaterialAlertDialogBuilder(this)
                    .setTitle("Verifikasi Berhasil")
                    .setMessage("Data akun berhasil disimpan ke SharedPreferences.")
                    .setPositiveButton("Ke Halaman Login") { _, _ ->
                        // 2. Perbaikan: Samakan nama variabel menjadi loginIntent
                        val loginIntent = Intent(this, LoginActivity::class.java)
                        loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(loginIntent)
                        finish()
                    }
                    .show()
            }
        }
    }

    private fun saveToSharedPreferences() {
        // Disamakan dengan LoginActivity ("user_pref" & "username")
        val sharedPreferences = getSharedPreferences("user_pref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("NAMA", nama)
        editor.putString("NO_HP", noHp)
        editor.putString("username", username)
        editor.putString("PASSWORD", password)
        editor.apply()
    }
}