package com.example.mungandnyang

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mungandnyang.databinding.ActivityReviewUploadBinding

class ReviewUploadActivity : AppCompatActivity() {
    lateinit var binding: ActivityReviewUploadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivRevupPicture.setImageURI(intent.getStringExtra("uploadImage") as Uri)
    }
}