package com.medwiz.medwizdoctor.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentBaseBinding
import com.medwiz.medwizdoctor.util.MedWizUtils
import com.medwiz.medwizdoctor.util.UtilConstants
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: Prithwiraj Nath
 * @Date:16/12/22
 */
@AndroidEntryPoint
class BaseFragment : Fragment(R.layout.fragment_base) {
    private lateinit var binding: FragmentBaseBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentBaseBinding.bind(view)

        val token= MedWizUtils.storeValueInPreference(requireContext(),
            UtilConstants.accessToken,"",false)
        val userId= MedWizUtils.storeValueInPreference(requireContext(),
            UtilConstants.userId,"0",false)

        if(token.isNotEmpty()&&userId.isNotEmpty()){
            findNavController().navigate(R.id.action_baseFragment_to_homeFragment)

        }else{
            //go to login
            findNavController().navigate(R.id.action_baseFragment_to_loginFragment)
        }
    }

}