package com.test.weatherforecastapp.ui.feature

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.test.weatherforecastapp.MainActivity
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }
}