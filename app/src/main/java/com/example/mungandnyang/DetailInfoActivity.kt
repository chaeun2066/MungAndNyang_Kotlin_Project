package com.example.mungandnyang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.mungandnyang.databinding.ActivityDetailInfoBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetailInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val database = FirebaseDatabase.getInstance().reference

        val adoptDAO = AdoptDAO()
        //Storage에 있는 이미지를 imgRef에 삽입
        val imgRef = adoptDAO.storage!!.reference.child("images/${intent.getStringExtra("docId")}.jpg")
        //해당 이미지를 Url로 다운받기
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

        // ReviewUploadActivity에서 후기를 작성한 유저의 UID 값을 업로드할 때 저장 //
        //** 해당 게시글을 작성한 유저의 Uid 값을 가진 Email 값을 적용 DAO **//
        database.child("user").child("${intent.getStringExtra("uId")}")
            .child("email").addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                   var email = snapshot.getValue(String::class.java)
                    binding.tvDeinfoId.text = email
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

        binding.tvDeinfoName.text = intent.getStringExtra("name")
        binding.tvDeinfoKind.text = intent.getStringExtra("breed")
        binding.tvDeinfoDate.text = intent.getStringExtra("date")
        binding.tvDeinfoText.text = intent.getStringExtra("text")
        binding.tvDeinfoAge.text = intent.getStringExtra("age") + " 세"
    }
}