package com.scdmonitor.app.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.scdmonitor.app.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Simple dashboard fragment showing risk level and basic vitals.
 */
@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val riskText = view.findViewById<TextView>(R.id.risk_text)

        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.riskProb.collect { p ->
                    riskText.text = "Risk: ${"%.0f".format(p*100)}%"
                }
            }
            launch {
                viewModel.riskLevel.collect { l ->
                    // color mapping handled by layout styles/resources in a full app
                }
            }
        }

        // Example: compute risk with placeholder vitals
        viewModel.computeRiskFromVitals(hr = 110.0, hrv = 20.0, spo2 = 95.0)
    }
}
