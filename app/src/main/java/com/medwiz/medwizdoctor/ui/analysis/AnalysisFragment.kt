package com.medwiz.medwizdoctor.ui.analysis

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentAnalysisBinding

/**
 * @Author: Prithwiraj Nath
 * @Date:12/02/23
 */
class AnalysisFragment:Fragment(R.layout.fragment_analysis) {
    private lateinit var binding: FragmentAnalysisBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAnalysisBinding.bind(view)
    }
}