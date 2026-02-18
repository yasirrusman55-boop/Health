package com.scdmonitor.app.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*

/**
 * BleManager handles scanning for BLE devices and streaming parsed sensor data.
 * It can scan and connect to devices and parse common GATT characteristics such as
 * Heart Rate Measurement (UUID 0x2A37). Pulse Oximeter services vary by device.
 */
class BleManager(private val context: Context) {
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private val scanner: BluetoothLeScanner? = bluetoothAdapter?.bluetoothLeScanner

    private val _devices = MutableSharedFlow<BluetoothDevice>(replay = 0)
    val devices = _devices.asSharedFlow()

    private val _hrReadings = MutableSharedFlow<Int>(replay = 0)
    val hrReadings = _hrReadings.asSharedFlow()

    private val mainHandler = Handler(Looper.getMainLooper())

    @SuppressLint("MissingPermission")
    fun startScan() {
        scanner?.startScan(scanCallback)
    }

    @SuppressLint("MissingPermission")
    fun stopScan() {
        scanner?.stopScan(scanCallback)
    }

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            result?.device?.let { d ->
                CoroutineScope(Dispatchers.Default).launch {
                    _devices.emit(d)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun connectAndSubscribe(device: BluetoothDevice) {
        mainHandler.post {
            device.connectGatt(context, false, object : BluetoothGattCallback() {
                override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                    if (newState == android.bluetooth.BluetoothProfile.STATE_CONNECTED) {
                        gatt.discoverServices()
                    } else {
                        gatt.close()
                    }
                }

                override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        for (service in gatt.services) {
                            handleService(gatt, service)
                        }
                    }
                }

                override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
                    parseCharacteristic(characteristic)
                }

                override fun onCharacteristicRead(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, status: Int) {
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        parseCharacteristic(characteristic)
                    }
                }
            })
        }
    }

    private fun handleService(gatt: BluetoothGatt, service: BluetoothGattService) {
        // Subscribe to Heart Rate Measurement characteristic if present
        val hrChar = service.getCharacteristic(UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb"))
        if (hrChar != null) {
            gatt.setCharacteristicNotification(hrChar, true)
            val cccd = hrChar.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"))
            cccd?.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
            try { gatt.writeDescriptor(cccd) } catch (_: Exception) {}
        }
        // Note: SpO2 characteristic/service UUIDs vary; vendor devices often use custom UUIDs.
    }

    private fun parseCharacteristic(characteristic: BluetoothGattCharacteristic) {
        val uuid = characteristic.uuid.toString().lowercase(Locale.US)
        when (uuid) {
            "00002a37-0000-1000-8000-00805f9b34fb" -> { // Heart Rate Measurement
                val data = characteristic.value ?: return
                val hr = parseHeartRateMeasurement(data)
                CoroutineScope(Dispatchers.Default).launch { _hrReadings.emit(hr) }
            }
            else -> {
                // unknown characteristic; vendors may implement SpO2 here
            }
        }
    }
    companion object {
        /**
         * Public helper to parse Heart Rate Measurement payload (GATT 0x2A37).
         * Kept public for unit testing and instrumentation testing.
         */
        fun parseHeartRateMeasurement(data: ByteArray): Int {
            if (data.isEmpty()) return -1
            val flags = data[0].toInt()
            val formatUInt16 = flags and 0x01 == 1
            return if (formatUInt16 && data.size >= 3) {
                ((data[2].toInt() and 0xFF) shl 8) or (data[1].toInt() and 0xFF)
            } else if (!formatUInt16 && data.size >= 2) {
                data[1].toInt() and 0xFF
            } else -1
        }
    }
}

