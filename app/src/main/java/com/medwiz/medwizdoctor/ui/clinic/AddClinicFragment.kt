package com.medwiz.medwizdoctor.ui.clinic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.medwiz.medwiz.model.ProfileItemModel

import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentAddClinicBinding
import com.medwiz.medwizdoctor.databinding.FragmentProfileBinding
import com.medwiz.medwizdoctor.ui.profile.ProfileItemAdapter
import com.medwiz.medwizdoctor.util.MedWizUtils
import com.medwiz.medwizdoctor.util.UtilConstants
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddClinicFragment:Fragment(R.layout.fragment_add_clinic) {
    private lateinit var binding: FragmentAddClinicBinding
    private var adapter: ClinicItemAdapter?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddClinicBinding.bind(view)

        binding.tvBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btSave.setOnClickListener {

        }

        binding.btAddClinic.setOnClickListener {
          findNavController().navigate(R.id.action_addClinicFragment_to_clinicDetailsFragment)
        }
        val profileItem1= ProfileItemModel(UtilConstants.ITEM_PROFILE,"Notification",R.drawable.ic_notification)
        val profileItem2= ProfileItemModel(UtilConstants.ITEM_EDIT_PROFILE,"Privacy & Policy",R.drawable.ic_privacy)
        val profileItem3= ProfileItemModel(UtilConstants.ITEM_SETTING,"Help Center",R.drawable.ic_help_center)
        val lis= ArrayList<ProfileItemModel>()
        lis.add(profileItem1)
        lis.add(profileItem2)
        lis.add(profileItem3)
        adapter = ClinicItemAdapter(requireActivity(),lis)
        binding.rcvClinic.adapter = adapter
        binding.rcvClinic.layoutManager = LinearLayoutManager(requireActivity())

    }





}