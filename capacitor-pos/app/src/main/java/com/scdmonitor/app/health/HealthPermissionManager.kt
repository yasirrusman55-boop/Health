package com.scdmonitor.app.health

import android.app.Activity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

/**
 * Helper to request Health Connect permissions from an Activity. Uses Activity Result APIs.
 */
class HealthPermissionManager(private val activity: Activity) {
    private var launcher: ActivityResultLauncher<Array<String>>? = null

    fun createLauncher(onResult: (Boolean) -> Unit) {
        launcher = activity.activityResultRegistry.register(
            "health_permissions",
            ActivityResultContracts.RequestMultiplePermissions()
        ) { perms ->
            onResult(perms.values.all { it })
        }
    }

    fun request(permissions: Array<String>) {
        launcher?.launch(permissions)
    }
}
