package com.scdmonitor.app.health

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.OxygenSaturationRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.Bpm
import androidx.health.connect.client.units.Percent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant

/**
 * HealthConnectManager uses the Health Connect client to read latest vitals.
 * Requires `androidx.health:health-connect-client` dependency and proper runtime permissions.
 */
class HealthConnectManager(private val context: Context) {

    private val client: HealthConnectClient? by lazy {
        try {
            HealthConnectClient.getOrCreate(context)
        } catch (e: Exception) {
            null
        }
    }

    /** Read the most recent heart rate in bpm, or null if unavailable. */
    suspend fun readLatestHeartRate(): Double? = withContext(Dispatchers.IO) {
        val c = client ?: return@withContext null
        try {
            val endTime = Instant.now()
            val req = ReadRecordsRequest(HeartRateRecord::class, TimeRangeFilter.between(endTime.minusSeconds(3600), endTime))
            val res = c.readRecords(req)
            val records = res.records
            val latest = records.maxByOrNull { it.time }
            return@withContext latest?.samples?.lastOrNull()?.value?.toDouble()
        } catch (e: Exception) {
            return@withContext null
        }
    }

    /** Read the most recent SpO2 percentage, or null if unavailable. */
    suspend fun readLatestSpO2(): Double? = withContext(Dispatchers.IO) {
        val c = client ?: return@withContext null
        try {
            val endTime = Instant.now()
            val req = ReadRecordsRequest(OxygenSaturationRecord::class, TimeRangeFilter.between(endTime.minusSeconds(3600), endTime))
            val res = c.readRecords(req)
            val records = res.records
            val latest = records.maxByOrNull { it.time }
            return@withContext latest?.samples?.lastOrNull()?.value?.toDouble()
        } catch (e: Exception) {
            return@withContext null
        }
    }

    /** Approximate total sleep minutes in last 24 hours. */
    suspend fun readSleepMinutesLast24h(): Int? = withContext(Dispatchers.IO) {
        val c = client ?: return@withContext null
        try {
            val endTime = Instant.now()
            val startTime = endTime.minusSeconds(24 * 3600)
            val req = ReadRecordsRequest(SleepSessionRecord::class, TimeRangeFilter.between(startTime, endTime))
            val res = c.readRecords(req)
            val records = res.records
            var totalMinutes = 0
            for (rec in records) {
                val dur = java.time.Duration.between(rec.startTime, rec.endTime ?: rec.startTime).toMinutes()
                totalMinutes += dur.toInt()
            }
            return@withContext totalMinutes
        } catch (e: Exception) {
            return@withContext null
        }
    }
}

