package com.medwiz.medwizdoctor.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.medwiz.medwiz.model.Consultation
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentHomeBinding
import com.medwiz.medwizdoctor.model.LoginResponse
import com.medwiz.medwizdoctor.ui.main.MainActivity
import com.medwiz.medwizdoctor.util.MedWizUtils
import com.medwiz.medwizdoctor.util.Resource
import com.medwiz.medwizdoctor.util.UtilConstants
import com.medwiz.medwizdoctor.viewmodels.ConsultationViewModel
import com.medwiz.medwizdoctor.viewmodels.DoctorViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: Prithwiraj Nath
 * @Date:16/12/22
 */
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home),ConsultationListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: DoctorViewModel by viewModels()
    private var adapter: PatientAdapter?=null
    private var userDetails: LoginResponse?=null
    private val consultationViewModel: ConsultationViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentHomeBinding.bind(view)
        val token= MedWizUtils.storeValueInPreference(requireContext(),
            UtilConstants.accessToken,"",false)
        val email= MedWizUtils.storeValueInPreference(requireContext(),
            UtilConstants.email,"",false)
        crateAdapter()
        consultationViewModel.consultationList.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading->{
                    (activity as MainActivity).showLoading()
                }

                is Resource.Success->{
                    (activity as MainActivity).hideLoading()
                    if(it.data!=null&&it.data.isNotEmpty()){
                         adapter!!.setData(it.data)
                    }




                }
                is Resource.Error->{
                    (activity as MainActivity).hideLoading()
                    if(it.message==UtilConstants.unauthorized){
                        MedWizUtils.performLogout(requireContext(),requireActivity())
                    }

                }
            }
        })
        viewModel.getDoctorByEmail(token,email)

        viewModel.getDoctor.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading->{
                    (activity as MainActivity).showLoading()
                }

                is Resource.Success->{
                    (activity as MainActivity).hideLoading()
                    this.userDetails=it.data!!
                    (activity as MainActivity).setUserDetails(this.userDetails!!)
                    val name="Hi "+userDetails!!.firstname+" "+userDetails!!.lastname
                    MedWizUtils.storeValueInPreference(requireContext(),
                        UtilConstants.docId,userDetails!!.id.toString(),true)
                    binding.tvDoctorName.text= name
                    if(userDetails!!.id>0){
                        consultationViewModel.getConsultationByDocId(token,userDetails!!.id,UtilConstants.STATUS_UPCOMING)
                    }

                }
                is Resource.Error->{
                    (activity as MainActivity).hideLoading()
                    if(it.message==UtilConstants.unauthorized){
                        MedWizUtils.performLogout(requireContext(),requireActivity())
                    }
                }
            }
        })
    }

    private fun crateAdapter() {
        adapter = PatientAdapter(requireActivity(),UtilConstants.TYPE_UPCOMING,this)
        binding.rcvPatient.adapter = adapter
        binding.rcvPatient.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onClickConsult(position: Int, consultation: Consultation) {
        (activity as MainActivity).setConsultation(consultation)
        findNavController().navigate(R.id.action_homeFragment_to_fragmentAddPrescriptions)
    }
}