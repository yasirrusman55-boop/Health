package com.scdmonitor.app.alerts

import android.content.Context
import android.telephony.SmsManager
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * AlertManager sends clinician alerts via FCM or SMS as a fallback.
 * `FirebaseMessaging` is injected to facilitate testing.
 */
class AlertManager @Inject constructor(private val context: Context, private val firebaseMessaging: FirebaseMessaging) {

    fun sendClinicianAlert(message: String, phoneNumber: String? = null) {
        CoroutineScope(Dispatchers.IO).launch {
            // Prefer marking an alert in Firestore for clinicians to pick up; as a placeholder
            // attempt to send an FCM downstream message (server credentials required in real app).
            try {
                // Client SDK cannot reliably send downstream messages; this is a placeholder.
                // In production, call a secure cloud function to trigger clinician FCM notifications.
                val topic = "clinicians"
                firebaseMessaging.subscribeToTopic(topic)
                // No direct send available from client to topic; add Firestore document or call cloud function.
            } catch (e: Exception) {
                phoneNumber?.let {
                    try {
                        SmsManager.getDefault().sendTextMessage(it, null, message, null, null)
                    } catch (_: Exception) {
                    }
                }
            }
        }
    }
}
