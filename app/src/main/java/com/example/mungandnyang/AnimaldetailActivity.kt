package com.example.mungandnyang

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.mungandnyang.databinding.ActivityAnimaldetailBinding
import com.example.mungandnyang.databinding.ActivityDetailInfoBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AnimaldetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityAnimaldetailBinding
    lateinit var animalDatabase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimaldetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animalDatabase = Firebase.database.reference


        if(intent.hasExtra("number") && intent.hasExtra("name") && intent.hasExtra("gender")
            && intent.hasExtra("breed") && intent.hasExtra("weight") && intent.hasExtra("age")
            && intent.hasExtra("date")){
                val number = intent.getStringExtra("number")!!

                binding.tvName.setText(intent.getStringExtra("name"))
                binding.tvGender.setText(intent.getStringExtra("gender"))
                binding.tvBreed.setText(intent.getStringExtra("breed"))
                binding.tvWeight.setText(intent.getStringExtra("weight")+"kg")
                binding.tvNumber.setText(number)
                binding.tvAge.setText(intent.getStringExtra("age"))
                binding.tvDate.setText(intent.getStringExtra("date"))

                animalDatabase.child("AdoptPhoto").addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for(postSnapshot in snapshot.children){
                            val currentAnimal = postSnapshot.getValue(PhotoVO::class.java)
                            if(currentAnimal!!.ani_number == number){
                                Glide.with(applicationContext).load("https://${currentAnimal!!.photo_url}").override(500,400).into(binding.ivPicture)
                            }
                        }
                    }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }

        binding.btnGuide.setOnClickListener {
            val intent = Intent(this, AdoptguideActivity::class.java)
            startActivity(intent)
        }

        binding.btnCite.setOnClickListener {
            val number = intent.getStringExtra("number")!!.substring(0 until 4)

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://animal.seoul.go.kr/animalInfo/view?aniNo=${number}"))
            startActivity(intent)
        }
    }
}