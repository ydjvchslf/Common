package com.poliotmia.common

import android.app.Application
import com.poliotmia.common.util.DebugLog
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CommonApp : Application() {
    private val logTag = CommonApp::class.simpleName ?: ""

    override fun onCreate() {
        super.onCreate()
        DebugLog.i(logTag, "onCreate-()")
    }
}