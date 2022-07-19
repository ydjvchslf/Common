package com.poliotmia.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.poliotmia.common.databinding.ActivityLoginBinding
import com.poliotmia.common.util.DebugLog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val logTag = LoginActivity::class.simpleName ?: ""
    private lateinit var binding: ActivityLoginBinding
    // firebase 로그인
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DebugLog.i(logTag, "onCreate-()")
        //setContentView(R.layout.activity_main)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
//        if(currentUser != null){
//            reload()
//        }

        binding.loginBtn.setOnClickListener {
            login(binding.inputEmail.text.toString(), binding.inputPassword.text.toString())
        }

        binding.enrollBtn.setOnClickListener {
            createAccount(binding.inputEmail.text.toString(), binding.inputPassword.text.toString())
        }
    }

    // 로그인
    private fun login(email: String, pw: String) {
        if (email.isNotEmpty() && pw.isNotEmpty()) {
            DebugLog.i(logTag, "createAccount-()")
            DebugLog.d(logTag, "email: $email, pw: $pw")

            auth.signInWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        DebugLog.d(logTag, "signInWithEmail:success")
                        val user = auth.currentUser
                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        DebugLog.w(logTag, "signInWithEmail:failure \n ${task.exception}")
                        Toast.makeText(baseContext, "그런 계정 없슴다",
                            Toast.LENGTH_SHORT).show()
                        //updateUI(null)
                    }
                }
        }
    }

    // 계정 생성
    private fun createAccount(email: String, pw: String) {
        auth.createUserWithEmailAndPassword(email, pw)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    DebugLog.d(logTag, "createUserWithEmail:success")
                    val user = auth.currentUser
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    DebugLog.w(logTag, "createUserWithEmail:failure \n ${task.exception}")
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }

}