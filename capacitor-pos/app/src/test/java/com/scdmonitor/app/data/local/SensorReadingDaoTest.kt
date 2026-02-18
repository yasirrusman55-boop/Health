package com.scdmonitor.app.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.scdmonitor.app.data.local.entities.SensorReading
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SensorReadingDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var dao: SensorReadingDao

    @Before
    fun setup() {
        val ctx = ApplicationProvider.getApplicationContext<android.content.Context>()
        db = Room.inMemoryDatabaseBuilder(ctx, AppDatabase::class.java).build()
        dao = db.sensorReadingDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndGetById() = runBlockingTest {
        val r = SensorReading(timestamp = System.currentTimeMillis(), heartRate = 80, hrv = 45.0, spo2 = 98.0, temperature = 36.5, steps = 0, sleepMinutes = 0, source = "test")
        val id = dao.insert(r)
        val got = dao.getById(id)
        assertEquals(got?.heartRate, 80)
    }
}
