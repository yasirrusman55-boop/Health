package com.scdmonitor.app

import android.app.Application
import com.scdmonitor.app.data.remote.FirebaseManager
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class to initialize app-wide services and Hilt.
 */
@HiltAndroidApp
class ScdMonitorApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase and other global managers
        FirebaseManager.init(this)
    }
}
