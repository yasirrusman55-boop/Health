package com.scdmonitor.app.risk

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt

/**
 * RiskCalculator computes a probabilistic risk score using simple z-score based heuristics.
 * Replace with a validated ML model for production.
 */
object RiskCalculator {

    /**
     * Compute a risk probability in [0,1] using deviations from baseline.
     * @param value current measurement
     * @param baseline mean baseline value
     * @param sd baseline standard deviation
     */
    fun zScoreProbability(value: Double?, baseline: Double, sd: Double): Double {
        if (value == null || sd <= 0.0) return 0.0
        val z = abs((value - baseline) / sd)
        // Map z to probability using simple saturating function
        val p = 1.0 - 1.0 / (1.0 + z)
        return max(0.0, minOf(1.0, p))
    }

    /**
     * Aggregate multiple modality probabilities into a single risk probability.
     */
    fun aggregateProbabilities(probs: List<Double>): Double {
        if (probs.isEmpty()) return 0.0
        // Weighted average (equal weights for now)
        return probs.average().coerceIn(0.0, 1.0)
    }

    /**
     * Map probability to risk level string
     */
    fun levelFromProbability(p: Double): String {
        return when {
            p >= 0.7 -> "RED"
            p >= 0.4 -> "AMBER"
            else -> "GREEN"
        }
    }
}
