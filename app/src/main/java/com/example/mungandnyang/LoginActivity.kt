package com.example.mungandnyang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.mungandnyang.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    lateinit var userAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //인증 초기화
        userAuth = Firebase.auth

        //로그인 로고
        Glide.with(this).load(R.raw.mungnyang).override(700,700).into(binding.ivLAMung)

        //텍스트 회원가입 이벤트
        binding.tvLARegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        //버튼 로그인 이벤트
        binding.btnLALogin.setOnClickListener {
            val email = binding.edtLAEmail.text.toString()
            val password = binding.edtLAPassword.text.toString()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

//            login(email, password)
        }
    }

    //로그인
    private fun login(email: String, password: String) {
        userAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "로그인을 성공하였습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "로그인을 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    Log.d("mungnyang", "Error: ${task.exception}")
                }
            }
    }
}