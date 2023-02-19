package com.medwiz.medwizdoctor.ui.appointment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentHomeBinding
import com.medwiz.medwizdoctor.databinding.FragmentPatientDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: Prithwiraj Nath
 * @Date:12/02/23
 */
@AndroidEntryPoint
class PatientDetailsFragment: Fragment(R.layout.fragment_patient_details) {
    private lateinit var binding: FragmentPatientDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPatientDetailsBinding.bind(view)
        binding.tvBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}