package com.example.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.common.databinding.ActivityLoginBinding
import com.example.common.util.DebugLog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val logTag = LoginActivity::class.simpleName ?: ""
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DebugLog.i(logTag, "onCreate-()")
        //setContentView(R.layout.activity_main)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}