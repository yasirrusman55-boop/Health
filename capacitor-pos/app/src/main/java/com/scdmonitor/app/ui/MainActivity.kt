package com.scdmonitor.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity hosts the app fragments. Minimal single-activity skeleton.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.scdmonitor.app.R.layout.activity_main)

        // TODO: Set up navigation component; for now inflate dashboard fragment.
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(com.scdmonitor.app.R.id.container, com.scdmonitor.app.ui.dashboard.DashboardFragment())
                .commitNow()
        }
    }
}
