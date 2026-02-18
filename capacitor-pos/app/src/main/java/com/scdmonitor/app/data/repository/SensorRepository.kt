package com.scdmonitor.app.data.repository

import com.google.firebase.firestore.SetOptions
import com.scdmonitor.app.data.local.SensorReadingDao
import com.scdmonitor.app.data.local.entities.SensorReading
import com.scdmonitor.app.data.remote.FirebaseManager

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Repository exposing sensor data operations and sync.
 */
class SensorRepository(private val dao: SensorReadingDao) {

    fun getAllReadingsFlow(): Flow<List<SensorReading>> = dao.getAllFlow()

    suspend fun insertReading(reading: SensorReading) = withContext(Dispatchers.IO) {
        val id = dao.insert(reading)
        // Fire-and-forget sync to Firestore
        try {
            val map = mapOf(
                "timestamp" to reading.timestamp,
                "heartRate" to reading.heartRate,
                "hrv" to reading.hrv,
                "spo2" to reading.spo2,
                "temperature" to reading.temperature,
                "steps" to reading.steps,
                "source" to reading.source
            )
            FirebaseManager.firestore.collection("sensor_readings").document(id.toString())
                .set(map, SetOptions.merge())
        } catch (e: Exception) {
            // ignore for now; add retry/backoff in real implementation
        }
    }
}
