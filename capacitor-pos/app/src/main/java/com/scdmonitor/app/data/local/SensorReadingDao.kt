package com.scdmonitor.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scdmonitor.app.data.local.entities.SensorReading
import kotlinx.coroutines.flow.Flow

/**
 * DAO for sensor readings.
 */
@Dao
interface SensorReadingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reading: SensorReading): Long

    @Query("SELECT * FROM sensor_readings ORDER BY timestamp DESC")
    fun getAllFlow(): Flow<List<SensorReading>>

    @Query("SELECT * FROM sensor_readings WHERE timestamp >= :since ORDER BY timestamp DESC")
    fun getRecentFlow(since: Long): Flow<List<SensorReading>>

    @Query("SELECT * FROM sensor_readings WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): SensorReading?

    @Query("DELETE FROM sensor_readings")
    suspend fun deleteAll()
}
