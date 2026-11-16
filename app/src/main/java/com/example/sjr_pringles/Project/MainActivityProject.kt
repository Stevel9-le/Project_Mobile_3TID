package com.example.sjr_pringles.Project

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.sjr_pringles.Project.Home.OnBoardingFragment
import com.example.sjr_pringles.R
import com.example.sjr_pringles.databinding.ActivityMainProjectBinding

class MainActivityProject : AppCompatActivity() {

    private lateinit var binding: ActivityMainProjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.itemIconTintList = null
        binding.viewPagerMain.adapter = MainPagerAdapter(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.onboarding_container, OnBoardingFragment())
                .commit()
        }

        binding.viewPagerMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.bottomNavigation.menu.getItem(position).isChecked = true
            }
        })

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    binding.viewPagerMain.currentItem = 0
                    Toast.makeText(this, "Membuka Menu Home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_menu -> {
                    binding.viewPagerMain.currentItem = 1
                    Toast.makeText(this, "Membuka Menu Makanan & Minuman", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_log -> {
                    binding.viewPagerMain.currentItem = 2
                    Toast.makeText(this, "Membuka Logbook Keluhan", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_profile -> {
                    binding.viewPagerMain.currentItem = 3
                    Toast.makeText(this, "Membuka Profil", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // Menangani tombol Back saat BookingFragment atau QuickBillingFragment terbuka
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                    binding.onboardingContainer.visibility = View.GONE
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    fun tutupOnBoarding() {
        binding.onboardingContainer.visibility = View.GONE
    }

    fun pindahTab(index: Int) {
        binding.viewPagerMain.currentItem = index
    }

    fun bukaHalamanSub(fragment: Fragment) {
        binding.onboardingContainer.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction()
            .replace(R.id.onboarding_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}