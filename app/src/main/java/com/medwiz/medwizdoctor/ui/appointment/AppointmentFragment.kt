package com.medwiz.medwizdoctor.ui.appointment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentAppointmentBinding

/**
 * @Author: Prithwiraj Nath
 * @Date:12/02/23
 */
class AppointmentFragment:Fragment(R.layout.fragment_appointment) {
    private lateinit var binding: FragmentAppointmentBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAppointmentBinding.bind(view)
    }
}