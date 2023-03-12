package com.medwiz.medwizdoctor.ui.clinic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.medwiz.medwiz.model.ProfileItemModel

import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentAddClinicBinding
import com.medwiz.medwizdoctor.databinding.FragmentAddClinicDetailsBinding
import com.medwiz.medwizdoctor.util.UtilConstants
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ClinicDetailsFragment:Fragment(R.layout.fragment_add_clinic_details) {
    private lateinit var binding: FragmentAddClinicDetailsBinding
    private var adapter: ClinicTimingAdapter?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddClinicDetailsBinding.bind(view)

        binding.tvBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btSave.setOnClickListener {

        }

        binding.btAddTiming.setOnClickListener {

        }
        val profileItem1= ProfileItemModel(UtilConstants.ITEM_PROFILE,"Notification",R.drawable.ic_notification)
        val profileItem2= ProfileItemModel(UtilConstants.ITEM_EDIT_PROFILE,"Privacy & Policy",R.drawable.ic_privacy)
        val profileItem3= ProfileItemModel(UtilConstants.ITEM_SETTING,"Help Center",R.drawable.ic_help_center)
        val lis= ArrayList<ProfileItemModel>()
        lis.add(profileItem1)
        lis.add(profileItem2)
        lis.add(profileItem3)
        adapter = ClinicTimingAdapter(requireActivity(),lis)
        binding.rcvClinicTiming.adapter = adapter
        binding.rcvClinicTiming.layoutManager = LinearLayoutManager(requireActivity())

    }





}