package com.example.sjr_pringles.Project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sjr_pringles.R

class DeveloperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer)

        // Mengatur judul Action Bar jika ada
        supportActionBar?.title = "Profil Developer"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}