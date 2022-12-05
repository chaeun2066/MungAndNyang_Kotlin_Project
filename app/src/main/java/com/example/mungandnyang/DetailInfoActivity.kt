package com.example.mungandnyang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.mungandnyang.databinding.ActivityDetailInfoBinding

class DetailInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("no") && intent.hasExtra("name") && intent.hasExtra("gender") && intent.hasExtra("age")
            && intent.hasExtra("kind") && intent.hasExtra("date") && intent.hasExtra("imageUrl")){
            Glide.with(binding.root.context).load(intent.getStringExtra("imageUrl")).into(binding.ivDeinfoPicture)
            Log.d("com.example.database", "**${intent.getStringExtra("imageUrl")}")
            binding.tvDeinfoName.setText(intent.getStringExtra("name"))
            if(intent.hasExtra("gender").equals("${"W"}")){
                binding.ivDeinfoGender.setImageResource(R.drawable.female)
            }else{
                binding.ivDeinfoGender.setImageResource(R.drawable.male)
            }
            binding.tvDeinfoKind.setText(intent.getStringExtra("kind"))
            binding.tvDeinfoNumber.setText(intent.getStringExtra("no"))
            binding.tvDeinfoAge.setText(intent.getStringExtra("age"))
            binding.tvDeinfoDate.setText(intent.getStringExtra("date"))
        }
    }
}