package com.scdmonitor.app.ble

import org.junit.Assert.assertEquals
import org.junit.Test

class BleManagerTest {
    @Test
    fun parseHeartRate_8bit() {
        // Flags = 0 -> 8-bit, value = 72
        val data = byteArrayOf(0x00, 72.toByte())
        val hr = BleManager.parseHeartRateMeasurement(data)
        assertEquals(72, hr)
    }

    @Test
    fun parseHeartRate_16bit() {
        // Flags LSB = 1 -> 16-bit, value = 0x01 0x00 => 1
        val data = byteArrayOf(0x01, 0x00, 0x01)
        val hr = BleManager.parseHeartRateMeasurement(data)
        // bytes are little-endian in the parsing logic: data[1]=low, data[2]=high => value = 1
        assertEquals(1, hr)
    }
}
