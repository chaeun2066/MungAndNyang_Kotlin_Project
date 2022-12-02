package com.example.mungandnyang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bumptech.glide.Glide
import com.example.mungandnyang.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent= Intent( this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 9000)
        Glide.with(this).load(R.raw.dog).override(600,600).into(binding.ivIaDog)
        Glide.with(this).load(R.raw.mungnyang).override(400,400).into(binding.ivIaMung)
    }
}