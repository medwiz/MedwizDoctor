package com.medwiz.medwizdoctor.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentHomeBinding
import com.medwiz.medwizdoctor.ui.main.MainActivity
import com.medwiz.medwizdoctor.util.MedWizUtils
import com.medwiz.medwizdoctor.util.Resource
import com.medwiz.medwizdoctor.util.UtilConstants
import com.medwiz.medwizdoctor.viewmodels.DoctorViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: Prithwiraj Nath
 * @Date:12/02/23
 */
@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: DoctorViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        val token=MedWizUtils.storeValueInPreference(requireContext(), UtilConstants.accessToken,"",false)
        val userId=  MedWizUtils.storeValueInPreference(
            requireContext(),
            UtilConstants.userId,
           "",
            false
        )
        viewModel.getDoctorByEmail(token,userId)

        viewModel.getDoctor.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    (activity as MainActivity).showLoading()
                }

                is Resource.Success -> {
                    (activity as MainActivity).hideLoading()
                    (activity as MainActivity).setUserDetails(it.data)
                    val name= it.data!!.user.firstName+" "+it.data.user.lastName
                    binding.tvDocName.text=name
                    Glide.with(requireContext())
                        .load(it.data.user.imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(binding.imgProfilePic)

                    if(!it.data.isApproved){
                        MedWizUtils.showToast("Please Update your profile and clinic details!",requireActivity())
                        findNavController().navigate(R.id.action_homeFragment_to_editProfileFragment)
                    }
                }
                is Resource.Error -> {
                    (activity as MainActivity).hideLoading()
                    if(it.message==UtilConstants.unauthorized){
                        MedWizUtils.performLogout(requireContext(),requireActivity())
                    }else{
                    MedWizUtils.showErrorPopup(
                        requireActivity(),
                        it.message.toString()
                    )
                    }
                }
            }
        })
    }
}