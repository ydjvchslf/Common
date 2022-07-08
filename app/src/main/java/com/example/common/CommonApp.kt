package com.example.common

import android.app.Application
import com.example.common.util.DebugLog
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CommonApp : Application() {
    private val logTag = CommonApp::class.simpleName ?: ""

    override fun onCreate() {
        super.onCreate()
        DebugLog.i(logTag, "onCreate()")
    }
}