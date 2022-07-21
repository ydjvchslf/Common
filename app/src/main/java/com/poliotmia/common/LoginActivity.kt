package com.poliotmia.common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.poliotmia.common.databinding.ActivityLoginBinding
import com.poliotmia.common.util.DebugLog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val logTag = LoginActivity::class.simpleName ?: ""
    private lateinit var binding: ActivityLoginBinding
    // firebase 로그인
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    private var GOOGLE_LOGIN_CODE = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DebugLog.i(logTag, "onCreate-()")
        //setContentView(R.layout.activity_main)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        //GoogleSignInClient 객체 초기화
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) //기본 로그인 방식 사용
            .requestIdToken(getString(R.string.default_web_client_id))
            //requestIdToken :필수사항이다. 사용자의 식별값(token)을 사용하겠다.
            //(App이 구글에게 요청)
            .requestEmail()
            // 사용자의 이메일을 사용하겠다.(App이 구글에게 요청)
            .build()

        // 내 앱에서 구글의 계정을 가져다 쓸거니 알고 있어라!
        googleSignInClient = GoogleSignIn.getClient(this, gso)

    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
//        if(currentUser != null){
//            reload()
//        }

        // 일반 로그인
        binding.loginBtn.setOnClickListener {
            login(binding.inputEmail.text.toString(), binding.inputPassword.text.toString())
        }

        // 일반 회원가입
        binding.enrollBtn.setOnClickListener {
            createAccount(binding.inputEmail.text.toString(), binding.inputPassword.text.toString())
        }

        // 구글 로그인
        binding.googleBtn.setOnClickListener {
            googleLogin()
        }
    }

    // 로그인
    private fun login(email: String, pw: String) {
        if (email.isNotEmpty() && pw.isNotEmpty()) {
            DebugLog.i(logTag, "login-()")
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
        DebugLog.i(logTag, "createAccount-()")
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

    private fun googleLogin() {
        DebugLog.i(logTag, "googleLogin-()")
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        DebugLog.i(logTag, "onActivityResult-()")
        super.onActivityResult(requestCode, resultCode, data)
        DebugLog.d(logTag, "requestCode: $requestCode, resultCode: $resultCode, data: $data")
        if(requestCode == GOOGLE_LOGIN_CODE) {
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
            // result가 성공했을 때 이 값을 firebase에 넘겨주기
            if(result!!.isSuccess) {
                var account = result.signInAccount
                // Second step
                firebaseAuthWithGoogle(account)
            }
        }
    }

    private fun firebaseAuthWithGoogle(account : GoogleSignInAccount?) {
        DebugLog.i(logTag, "firebaseAuthWithGoogle-()")
        var credential = GoogleAuthProvider.getCredential(account?.idToken,null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful) {
                    // Login, 아이디와 패스워드가 맞았을 때
                    Toast.makeText(this,  "success", Toast.LENGTH_LONG).show()
                    moveMainPage(task.result?.user)
                } else {
                    // Show the error message, 아이디와 패스워드가 틀렸을 때
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    // 로그인이 성공 시 다음 페이지 이동
    private fun moveMainPage(user: FirebaseUser?) {
        DebugLog.i(logTag, "moveMainPage-()")
        // 파이어베이스 유저 상태가 있을 경우 다음 페이지로 넘어갈 수 있음
        if(user != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}