package com.example.mungandnyang

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.mungandnyang.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    lateinit var binding : ActivityRegisterBinding
    lateinit var userAuth : FirebaseAuth
    private lateinit var userDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //인증 초기화
        userAuth = Firebase.auth

        //데이터 베이스 초기화
        userDatabase = Firebase.database.reference

        //전화번호 입력 시 (-) 자동생성
        binding.edtRAPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        //텍스트 로그인 이벤트
        binding.tvRALogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        //버튼 회원가입 이벤트
        binding.btnRARegister.setOnClickListener {
            var manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            val passwordRegex = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@$!%*#?&.])[A-Za-z[0-9]@$!%*#?&.]{0,15}$")
            val emailRegex = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@.])[A-Za-z[0-9]@.]{8,30}$")
<<<<<<< HEAD
            val phoneRegex = Pattern.compile("^\\d{3}-\\d{3,4}-\\d{4}$")
=======
//            val phoneRegex = Pattern.compile("^(?=.*[0-9])(?=.*[-])[[0-9]-]{13,}$")
>>>>>>> 672147a16f96c8deb5bf3c76dea45f4a30bf68b8
            val name = binding.edtRAName.text.toString().trim()
            val email = binding.edtRAEmail.text.toString().trim()
            val password = binding.edtRAPassword.text.toString().trim()
            val phone = binding.edtRAPhone.text.toString().trim()



            if (email.isEmpty() || !emailRegex.matcher(email).matches()){
                binding.edtRAEmail.requestFocus()
                manager.showSoftInput(binding.edtRAEmail, InputMethodManager.SHOW_IMPLICIT)
                Toast.makeText(this, "올바른 이메일 형식으로 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty() || !passwordRegex.matcher(password).matches()){
<<<<<<< HEAD
                binding.edtRAPassword.text.clear()
                binding.edtRAPassword.requestFocus()
                manager.showSoftInput(binding.edtRAPassword, InputMethodManager.SHOW_IMPLICIT)
                Toast.makeText(this, "영문,숫자,특수문자를 조합하여 15자 이내로 입력해주세요", Toast.LENGTH_LONG).show()
            } else if (name.isEmpty()) {
                binding.edtRAName.requestFocus()
                manager.showSoftInput(binding.edtRAName, InputMethodManager.SHOW_IMPLICIT)
                Toast.makeText(this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (phone.isEmpty() || !phoneRegex.matcher(phone).matches()) {
                binding.edtRAPhone.requestFocus()
                manager.showSoftInput(binding.edtRAPhone, InputMethodManager.SHOW_IMPLICIT)
                Toast.makeText(this, "연락처를 정확히 입력해주세요", Toast.LENGTH_SHORT).show()
=======
                binding.edtRAPhone.requestFocus()
                manager.showSoftInput(binding.edtRAPhone, InputMethodManager.SHOW_IMPLICIT)
                Toast.makeText(this, "영문,숫자,특수문자를 조합하여 10~15자로 입력해주세요", Toast.LENGTH_LONG).show()
            } else if (name.isEmpty()) {
                binding.edtRAPhone.requestFocus()
                manager.showSoftInput(binding.edtRAPhone, InputMethodManager.SHOW_IMPLICIT)
                Toast.makeText(this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show()
//            } else if (phone.isEmpty() || !phoneRegex.matcher(password).matches()) {
//                binding.edtRAPassword.text.clear()
//                binding.edtRAPassword.requestFocus()
//                manager.showSoftInput(binding.edtRAPassword, InputMethodManager.SHOW_IMPLICIT)
//                Toast.makeText(this, "연락처를 정확히 입력해주세요", Toast.LENGTH_SHORT).show()
>>>>>>> 672147a16f96c8deb5bf3c76dea45f4a30bf68b8
            } else {
                register(name, email, password, phone)
            }
        }
    }

    //회원가입 기능
    private fun register(name: String, email: String, password: String, phone: String) {
        userAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "멍앤냥의 멤버가 되신 걸 축하해요!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    addUserToDatabase(name, email, phone, userAuth.currentUser?.uid!!)
                }else{
                    Toast.makeText(this, "회원가입에 실패했어요", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, phone: String, uId: String) {
        userDatabase.child("user").child(uId).setValue(User(name,email, phone, uId))
    }
}