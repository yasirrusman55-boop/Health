package com.scdmonitor.app.data.remote

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging

/**
 * Simple Firebase manager to provide Auth, Firestore and Messaging instances.
 */
object FirebaseManager {
    fun init(context: Context) {
        FirebaseApp.initializeApp(context)
    }

    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    val messaging: FirebaseMessaging by lazy { FirebaseMessaging.getInstance() }
}
