package com.scdmonitor.app.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.scdmonitor.app.data.local.AppDatabase
import com.scdmonitor.app.data.local.SensorReadingDao
import com.scdmonitor.app.data.remote.NetworkModule
import com.scdmonitor.app.data.remote.FirebaseManager
import com.scdmonitor.app.data.repository.SensorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * AppModule provides app-wide dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(appContext: Context): AppDatabase = AppDatabase.getDatabase(appContext)

    @Provides
    @Singleton
    fun provideSensorReadingDao(db: AppDatabase): SensorReadingDao = db.sensorReadingDao()

    @Provides
    @Singleton
    fun provideSensorRepository(dao: SensorReadingDao): SensorRepository = SensorRepository(dao)

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseMessaging(): FirebaseMessaging = FirebaseMessaging.getInstance()

    @Provides
    @Singleton
    fun provideApiService(): com.scdmonitor.app.data.remote.ApiService = NetworkModule.apiService
}
