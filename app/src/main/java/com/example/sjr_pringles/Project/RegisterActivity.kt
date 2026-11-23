package com.example.sjr_pringles.Project

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sjr_pringles.Project.Network.SupabaseClient
import com.example.sjr_pringles.Project.Network.UserRequest
import com.example.sjr_pringles.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val nama = binding.etNama.text.toString().trim()
            val noHp = binding.etNoHp.text.toString().trim()
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (nama.isEmpty() || noHp.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userBaru = UserRequest(nama, noHp, username, password)

            SupabaseClient.api.registerUser(userBaru).enqueue(object : Callback<List<UserRequest>> {
                override fun onResponse(call: Call<List<UserRequest>>, response: Response<List<UserRequest>>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegisterActivity, "Berhasil terdaftar ke Supabase!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterActivity, OtpActivity::class.java).apply {
                            putExtra("NAMA", nama)
                            putExtra("NO_HP", noHp)
                            putExtra("USERNAME", username)
                            putExtra("PASSWORD", password)
                        }
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@RegisterActivity, "Gagal register (${response.code()})", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<UserRequest>>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "Error koneksi: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}