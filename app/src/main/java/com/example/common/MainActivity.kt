package com.example.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.common.util.DebugLog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val logTag = MainActivity::class.simpleName ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DebugLog.i(logTag, "onCreate-()")
        setContentView(R.layout.activity_main)
    }
}