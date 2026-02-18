package com.scdmonitor.app.risk

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Placeholder anomaly detector. In production this would host a trained model
 * or use on-device ML to detect patterns preceding VOC.
 */
class AnomalyDetector {

    suspend fun isAnomalousSeries(values: List<Double>): Boolean = withContext(Dispatchers.Default) {
        if (values.size < 5) return@withContext false
        val mean = values.average()
        val sd = sqrt(values.map { (it - mean) * (it - mean) }.average())
        // naive rule: recent value deviates > 2.5 sd
        val last = values.last()
        return@withContext sd > 0 && kotlin.math.abs(last - mean) > 2.5 * sd
    }
}
