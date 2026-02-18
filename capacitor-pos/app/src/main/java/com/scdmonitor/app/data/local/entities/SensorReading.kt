package com.scdmonitor.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * SensorReading represents a single timestamped measurement from a wearable or Health Connect.
 */
@Entity(tableName = "sensor_readings")
data class SensorReading(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    val heartRate: Int?,
    val hrv: Double?,
    val spo2: Double?,
    val temperature: Double?,
    val steps: Int?,
    val sleepMinutes: Int?,
    val source: String // e.g., "ble:device123" or "healthconnect"
)
