package com.poliotmia.common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.poliotmia.common.databinding.ActivityMainBinding
import com.poliotmia.common.util.DebugLog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val logTag = MainActivity::class.simpleName ?: ""
    private lateinit var binding: ActivityMainBinding
    // firebase
    private lateinit var auth: FirebaseAuth
    // navigation 전역변수로 수정
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DebugLog.i(logTag, "onCreate-()")
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //네비게이션 담는 호스트
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

        //네비 컨트롤러
        navController = navHostFragment.navController

        //바텀 네비에션 뷰와 네비게이션 묶어줌
        NavigationUI.setupWithNavController(binding.bottomNavi, navController)

        //커스텀 액션바
        setSupportActionBar(binding.toolbar) //커스텀한 toolbar를 액션바로 사용
        supportActionBar?.setDisplayShowTitleEnabled(false) //액션바에 표시되는 제목의 표시유무를 설정합니다. false로 해야 custom한 툴바의 이름이 화면에 보이게 됩니다.
        binding.toolbar.title = "~커스텀App~"

        // Initialize Firebase Auth
        auth = Firebase.auth
    }

    // 액션바 메뉴 액션바에 집어넣기
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // 액션바 메뉴 클릭이벤트
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_logout -> {
                DebugLog.i(logTag, "logoutSelected-()")
                auth.signOut()
                val currentUser = auth.currentUser
                // 로그인화면 전환
                moveLoginPage(currentUser)
                super.onOptionsItemSelected(item)
            }
            R.id.action_profile -> {
                DebugLog.i(logTag, "profileSelected-()")
                // 프로필 화면 전환
                navController.navigate(R.id.profileFragment)
                super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveLoginPage(user: FirebaseUser?) {
        DebugLog.i(logTag, "moveLoginPage-()")
        if(user == null) {
            finishAffinity()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}