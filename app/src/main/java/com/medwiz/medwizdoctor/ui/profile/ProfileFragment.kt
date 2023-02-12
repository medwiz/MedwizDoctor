package com.medwiz.medwizdoctor.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.medwiz.medwiz.model.ProfileItemModel

import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentProfileBinding
import com.medwiz.medwizdoctor.util.MedWizUtils
import com.medwiz.medwizdoctor.util.UtilConstants
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ProfileFragment:Fragment(R.layout.fragment_profile),ProfileItemListener {
    private lateinit var binding: FragmentProfileBinding
    private var adapter: ProfileItemAdapter?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        binding.imgLogout.setOnClickListener {
            MedWizUtils.performLogout(requireContext(),requireActivity())
        }
        val profileItem1= ProfileItemModel(UtilConstants.ITEM_PROFILE,"Notification",R.drawable.ic_notification)
        val profileItem2= ProfileItemModel(UtilConstants.ITEM_EDIT_PROFILE,"Privacy & Policy",R.drawable.ic_privacy)
        val profileItem3= ProfileItemModel(UtilConstants.ITEM_SETTING,"Help Center",R.drawable.ic_help_center)
        val logout= ProfileItemModel(UtilConstants.ITEM_LOGOUT,"Change Password",R.drawable.ic_change_password)
        val profileItem4= ProfileItemModel(UtilConstants.ITEM_TERMS,"About Us",R.drawable.ic_about_us)
        val lis= ArrayList<ProfileItemModel>()
        lis.add(profileItem1)
        lis.add(profileItem2)
        lis.add(profileItem3)
        lis.add(logout)
        lis.add(profileItem4)
        adapter = ProfileItemAdapter(requireActivity(),lis,this)
        binding.rcvProfileList.adapter = adapter
        binding.rcvProfileList.layoutManager = LinearLayoutManager(requireActivity())

    }


    override fun onClickItem(position: Int, itemObj: ProfileItemModel) {
        val i=0
    }


}