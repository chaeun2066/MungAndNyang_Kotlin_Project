package com.example.mungandnyang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.mungandnyang.databinding.ActivityDetailInfoBinding

class DetailInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adoptDAO = AdoptDAO()
        val imgRef = adoptDAO.storage!!.reference.child("images/${intent.getStringExtra("docId")}.jpg")
        imgRef.downloadUrl.addOnCompleteListener {
            if(it.isSuccessful){
                Glide.with(applicationContext).load(it.result).centerCrop().into(binding.ivDeinfoPicture)
            }
        }

        if(intent.getStringExtra("gender").equals("W")){
            binding.ivDeinfoGender.setImageResource(R.drawable.female)
        }else{
            binding.ivDeinfoGender.setImageResource(R.drawable.male)
        }

        binding.tvDeinfoName.text = intent.getStringExtra("name")
        binding.tvDeinfoKind.text = intent.getStringExtra("breed")
        binding.tvDeinfoDate.text = intent.getStringExtra("date")
        binding.tvDeinfoText.text = intent.getStringExtra("text")

    }
}