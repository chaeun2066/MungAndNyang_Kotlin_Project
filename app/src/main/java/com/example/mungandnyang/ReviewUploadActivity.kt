package com.example.mungandnyang

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.mungandnyang.databinding.ActivityReviewUploadBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime.now

class ReviewUploadActivity : AppCompatActivity() {
    lateinit var binding: ActivityReviewUploadBinding
    var imageUri: Uri? = null
    var gender: String = ""
    val userAuth = Firebase.auth

    @RequiresApi(Build.VERSION_CODES.O) //DatePicker 때 사용
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //** ReviewUploadActivity 실행 시 바로 갤러리 오픈 **//
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
            val items = arrayOf("남", "여", "모름")

            val eventHandler = object: DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, position: Int) {
                }
            }

            AlertDialog.Builder(binding.root.context).run{
                setTitle("성별 선택")
                setItems(items, object: DialogInterface.OnClickListener{
                    override fun onClick(dialogInterface: DialogInterface?, index: Int) {
                        when(items[index]){
                            "남" -> {
                                binding.ivRevupGender.setImageResource(R.drawable.male)
                                gender = "남"
                            }
                            "여" -> {
                                binding.ivRevupGender.setImageResource(R.drawable.female)
                                gender = "여"
                            }
                            "모름" -> {
                                binding.ivRevupGender.setImageResource(R.drawable.gender)
                                gender = "모름"
                            }
                        }
                    }
                })
                setPositiveButton("닫기", eventHandler)
                show()
            }
        }

        binding.tvRevupDate.setOnClickListener {
            DatePickerDialog(binding.root.context, object: DatePickerDialog.OnDateSetListener {
                override fun onDateSet(datePicker: DatePicker?, yyyy: Int, MM: Int, dd: Int) {
                    var year = yyyy
                    var month = MM + 1
                    var date = dd
                    Log.d("IU Player", "year: ${year}, month: ${month}, date: ${date}")
                    if(date < 10){
                        binding.tvRevupDate.setText("${year}-${month}-0${date}")
                    }else{
                        binding.tvRevupDate.setText("${year}-${month}-${date}")
                    }
                }
            }, now().year, now().month.value, now().dayOfMonth).show()
        }

        //등록 버튼
        binding.btnRevupReg.setOnClickListener {
            if(binding.ivRevupPicture.drawable != null && binding.tvRevupDate.text.isNotEmpty()){
                val adoptDAO = AdoptDAO()
                val docId = adoptDAO.reviewDbReference?.push()?.key
                val uId = userAuth.currentUser?.uid.toString() // 게시글 작성한 사람의 uid 값
                val name = binding.tvRevupName.text.toString()
                val breed = binding.tvRevupKind.text.toString()
                val date = binding.tvRevupDate.text.toString()
                val text = binding.tvRevupText.text.toString()
                val age = binding.tvRevupAge.text.toString()
                val uploadData = UploadVO(docId, uId, name, gender, breed, date, text, age)
                //storage에 이미지 등록
                val uploadImg = adoptDAO.storage!!.reference.child("images/${uploadData.docId}.jpg")
                uploadImg.putFile(imageUri!!)?.addOnSuccessListener{
                    // ** 후기 게시글 Firebase 등록 DAO ** //
                    adoptDAO.reviewDbReference?.child(docId!!)?.setValue(uploadData)?.addOnSuccessListener {
                        Log.d("mungandnyang","데이터 전송 성공")
                        Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    }?.addOnFailureListener {
                        Log.d("mungandnyang","데이터 전송 오류")
                    }
                }?.addOnFailureListener {
                    Log.d("mungandnyang","파일 전송 오류")
                }
            }else{
                Toast.makeText(this, "사진과 날짜를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}