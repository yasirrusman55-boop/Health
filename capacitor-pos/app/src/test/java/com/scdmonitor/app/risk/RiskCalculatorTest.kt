package com.scdmonitor.app.risk

import org.junit.Assert.assertEquals
import org.junit.Test

class RiskCalculatorTest {
    @Test
    fun zScoreProbability_zeroSd_returnsZero() {
        val p = RiskCalculator.zScoreProbability(100.0, baseline = 100.0, sd = 0.0)
        assertEquals(0.0, p, 1e-6)
    }

    @Test
    fun aggregateProbabilities_average() {
        val p = RiskCalculator.aggregateProbabilities(listOf(0.1, 0.5, 0.9))
        assertEquals(0.5, p, 1e-6)
    }
}
