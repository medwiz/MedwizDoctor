package com.medwiz.medwizdoctor.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentPatientDetailsBinding

/**
 * @Author: Prithwiraj Nath
 * @Date:19/02/23
 */
class PatientHistoryFragment:Fragment(R.layout.fragment_ptient_history) {
    private lateinit var binding:FragmentPatientDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentPatientDetailsBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}