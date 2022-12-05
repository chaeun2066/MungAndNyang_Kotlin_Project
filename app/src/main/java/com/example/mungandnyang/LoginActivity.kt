package com.example.mungandnyang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.example.mungandnyang.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

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
            val passwordRegex = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@$!%*#?&.])[A-Za-z[0-9]@$!%*#?&.]{10,15}$")
            val emailRegex = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@.])[A-Za-z[0-9]@.]{8,30}$")
            var manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            val email = binding.edtLAEmail.text.toString()
            val password = binding.edtLAPassword.text.toString()

            if (email.isEmpty()) {
                binding.edtLAEmail.requestFocus()
                manager.showSoftInput(binding.edtLAEmail, InputMethodManager.SHOW_IMPLICIT)
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty() ) {
                binding.edtLAPassword.requestFocus()
                manager.showSoftInput(binding.edtLAPassword, InputMethodManager.SHOW_IMPLICIT)
                Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            }else if(!emailRegex.matcher(email).matches()){
                binding.edtLAEmail.requestFocus()
                manager.showSoftInput(binding.edtLAEmail, InputMethodManager.SHOW_IMPLICIT)
                Toast.makeText(this, "올바르지 않은 이메일 형식입니다.", Toast.LENGTH_SHORT).show()
            }else if(!passwordRegex.matcher(password).matches()){
                binding.edtLAEmail.requestFocus()
                manager.showSoftInput(binding.edtLAEmail, InputMethodManager.SHOW_IMPLICIT)
                Toast.makeText(this, "잘못된 비밀번호입니다.", Toast.LENGTH_SHORT).show()
            }else{
                login(email, password)
            }
        }
    }

    //로그인
    private fun login(email: String, password: String) {
        userAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "멍앤냥에 오신걸 환영합니다!", Toast.LENGTH_SHORT).show()
                    finish()
                }else {
                    Toast.makeText(this, "이메일 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                    Log.d("mungnyang", "Error: ${task.exception}")
                }

            }
    }
}