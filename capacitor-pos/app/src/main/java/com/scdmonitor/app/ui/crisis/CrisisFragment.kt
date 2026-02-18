package com.scdmonitor.app.ui.crisis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.scdmonitor.app.R

/**
 * CrisisFragment shows crisis details and allows confirmation of auto-logged events.
 */
class CrisisFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_crisis, container, false)
    }
}
