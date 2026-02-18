package com.scdmonitor.app.ui.careplan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.scdmonitor.app.R

/**
 * CarePlanFragment shows the digital care plan and actions.
 */
class CarePlanFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_careplan, container, false)
    }
}
