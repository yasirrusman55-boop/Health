package com.scdmonitor.app.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scdmonitor.app.data.repository.SensorRepository
import com.scdmonitor.app.risk.RiskCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the dashboard exposing vitals and aggregated risk.
 * Injected via Hilt for easier testing and decoupling.
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: SensorRepository) : ViewModel() {

    private val _riskProb = MutableStateFlow(0.0)
    val riskProb: StateFlow<Double> = _riskProb

    private val _riskLevel = MutableStateFlow("GREEN")
    val riskLevel: StateFlow<String> = _riskLevel

    fun computeRiskFromVitals(hr: Double?, hrv: Double?, spo2: Double?) {
        viewModelScope.launch {
            // Use simple baseline placeholders; in real app compute personalized baselines
            val hrProb = RiskCalculator.zScoreProbability(hr, baseline = 75.0, sd = 10.0)
            val hrvProb = RiskCalculator.zScoreProbability(hrv, baseline = 50.0, sd = 12.0)
            val spo2Prob = RiskCalculator.zScoreProbability(spo2, baseline = 98.0, sd = 1.5)
            val agg = RiskCalculator.aggregateProbabilities(listOf(hrProb, hrvProb, spo2Prob))
            _riskProb.value = agg
            _riskLevel.value = RiskCalculator.levelFromProbability(agg)
        }
    }
}
