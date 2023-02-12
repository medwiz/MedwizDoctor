package com.medwiz.medwizdoctor.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: Prithwiraj Nath
 * @Date:12/02/23
 */
@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
    }
}