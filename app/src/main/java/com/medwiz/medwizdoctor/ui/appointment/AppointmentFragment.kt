package com.medwiz.medwizdoctor.ui.appointment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.medwiz.medwiz.model.Consultation
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentAppointmentBinding
import com.medwiz.medwizdoctor.ui.main.MainActivity
import com.medwiz.medwizdoctor.util.MedWizUtils
import com.medwiz.medwizdoctor.util.Resource
import com.medwiz.medwizdoctor.util.UtilConstants
import com.medwiz.medwizdoctor.viewmodels.ConsultationViewModel

/**
 * @Author: Prithwiraj Nath
 * @Date:12/02/23
 */
class AppointmentFragment:Fragment(R.layout.fragment_appointment),AppointmentListener {
    private lateinit var binding: FragmentAppointmentBinding
    private val viewModel: ConsultationViewModel by viewModels()
    private var adapter: AppointmentAdapter?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAppointmentBinding.bind(view)
        val token= MedWizUtils.storeValueInPreference(requireContext(),
            UtilConstants.accessToken,"",false)
        binding.tab.addTab(binding.tab.newTab().setText("UPCOMING"))
        binding.tab.addTab(binding.tab.newTab().setText("COMPLETED"))
        binding.tab.addTab(binding.tab.newTab().setText("CANCELED"))
        createAdapter("")

        binding.tab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val i=tab!!.position
                val k=tab!!.text.toString()
                Log.d("prithwi onTabSelected",tab!!.text.toString())
                when(tab!!.text.toString()){
                    "UPCOMING"->{
                        val list:ArrayList<String> = java.util.ArrayList()
                        list.add("One")
                        list.add("Two")
                        list.add("Three")
                        list.add("Four")
                        list.add("Five")
                        list.add("Six")
                        adapter!!.setData(list,UtilConstants.TYPE_UPCOMING)
                    }
                    "COMPLETED"->{
                        val list:ArrayList<String> = java.util.ArrayList()
                        list.add("One")
                        list.add("Two")
                        list.add("Three")
                        list.add("Four")
                        list.add("Five")
                        list.add("Six")
                        adapter!!.setData(list,UtilConstants.TYPE_COMPLETED)
                    }
                    "CANCELED"->{
                        val list:ArrayList<String> = java.util.ArrayList()
                        list.add("One")
                        list.add("Two")
                        list.add("Three")
                        list.add("Four")
                        list.add("Five")
                        list.add("Six")
                        adapter!!.setData(list,UtilConstants.TYPE_CANCELLED)
                    }

                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("prithwi onTabUnselected",tab.toString())
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("prithwi onTabReselected",tab.toString())
            }

        })

    }

    private fun createAdapter(toString: String) {
        adapter = AppointmentAdapter(requireActivity(),this)
        binding.rcvAppointment.adapter = adapter
        binding.rcvAppointment.layoutManager = LinearLayoutManager(requireActivity())
        val list:ArrayList<String> = java.util.ArrayList()
        list.add("One")
        list.add("Two")
        list.add("Three")
        list.add("Four")
        list.add("Five")
        list.add("Six")
        adapter!!.setData(list,UtilConstants.TYPE_UPCOMING)
    }

    override fun onClickItem(obj: String, position: Int) {
        findNavController().navigate(R.id.action_appointmentFragment_to_patientDetailsFragment)
    }

    override fun onClickHistory(obj: String, position: Int) {
        findNavController().navigate(R.id.action_appointmentFragment_to_patientHistoryFragment)
    }
}