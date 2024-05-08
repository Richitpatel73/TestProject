package com.example.dummytestapprichit.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.dummytestapprichit.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                finish()
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
            }
        }
        handler.postDelayed(runnable, 1000)
    }
}