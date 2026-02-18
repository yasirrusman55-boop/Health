package com.scdmonitor.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.scdmonitor.app.data.local.entities.CrisisEvent
import com.scdmonitor.app.data.local.entities.RiskScore
import com.scdmonitor.app.data.local.entities.SensorReading

/**
 * Room database for SCDMonitor. Uses encrypted storage in production (placeholder here).
 */
@Database(
    entities = [SensorReading::class, RiskScore::class, CrisisEvent::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sensorReadingDao(): SensorReadingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "scdmonitor.db"
                )
                // TODO: Hook up SQLCipher/SupportFactory here with AES key from EncryptionUtils.
                val db = builder.build()
                INSTANCE = db
                db
            }
        }
    }
}
