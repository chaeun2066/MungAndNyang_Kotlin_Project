package com.example.mungandnyang

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.mungandnyang.databinding.ActivityReviewUploadBinding

class ReviewUploadActivity : AppCompatActivity() {
    lateinit var binding: ActivityReviewUploadBinding
    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                Glide.with(applicationContext).load(it.data?.data).centerCrop().into(binding.ivRevupPicture)
                imageUri = it.data?.data
            }
        }

        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        requestLauncher.launch(intent)

        binding.ivRevupPicture.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            requestLauncher.launch(intent)
        }

        binding.ivRevupGender.setOnClickListener {

        }

        binding.btnRevupReg.setOnClickListener {
            if(binding.ivRevupPicture.drawable != null && binding.tvRevupDate.text.isNotEmpty()){
                val adoptDAO = AdoptDAO()
                val name = binding.tvRevupName.text.toString()
                val breed = binding.tvRevupKind.text.toString()
                val date = binding.tvRevupDate.text.toString()
                val text = binding.tvRevupText.text.toString()
                val uploadData = UploadVO(name, breed, date, text)
            }else{
                Toast.makeText(this, "사진과 날짜를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }


        }
    }
}