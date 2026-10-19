package com.example.sjr_pringles.Project

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.sjr_pringles.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Portal Pelanggan iCafe"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl("http://10.0.2.2:5173/")

            setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                if (scrollY > oldScrollY) {
                    binding.appBar.setExpanded(false, true)
                } else if (scrollY < oldScrollY) {
                    binding.appBar.setExpanded(true, true)
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}