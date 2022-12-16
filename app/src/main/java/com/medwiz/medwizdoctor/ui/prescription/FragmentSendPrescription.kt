package com.medwiz.medwizdoctor.ui.prescription

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.medwiz.medwiz.doctorsView.docotorUi.consult.PrescriptionAdapter
import com.medwiz.medwiz.doctorsView.docotorUi.consult.PrescriptionListener
import com.medwiz.medwiz.model.Consultation
import com.medwiz.medwiz.model.Medication
import com.medwiz.medwiz.viewmodels.PrescriptionViewModel
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentSendPrescriptionBinding
import com.medwiz.medwizdoctor.model.LoginResponse
import com.medwiz.medwizdoctor.ui.main.MainActivity
import com.medwiz.medwizdoctor.util.MedWizUtils
import com.medwiz.medwizdoctor.util.Resource
import com.medwiz.medwizdoctor.util.UtilConstants
import com.medwiz.medwizdoctor.viewmodels.ConsultationViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentSendPrescription :Fragment(R.layout.fragment_send_prescription),
    PrescriptionListener {
    private lateinit var binding: FragmentSendPrescriptionBinding
    private var medicineAdapter: PrescriptionAdapter?=null
    private var labAdapter: PrescriptionAdapter?=null
    private var medicineList=ArrayList<Medication>()
    private var labTestList=ArrayList<Medication>()
    private var token:String=""
    private var consultation:Consultation?=null
    private var userDetails: LoginResponse?=null
    private val consultationViewModel: ConsultationViewModel by viewModels()
    private val prescriptionViewModel: PrescriptionViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSendPrescriptionBinding.bind(view)
        medicineList =  (activity as MainActivity).getMedicineList()
        labTestList= (activity as MainActivity).getTestList()
        userDetails=(activity as MainActivity).getUserDetails()
        consultation=(activity as MainActivity).getConsultation()
        setUi()


        binding.imgBack.setOnClickListener{
             goBack()
        }
        binding.btSend.setOnClickListener{
           createPrescription()

        }
        binding.tvPreview.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentSendPrescription_to_previewPrescriptionFragment)
        }

        medicineAdapter = PrescriptionAdapter(requireContext(),getString(R.string.add_medicine_title),this)
        binding.rcvMedicine.adapter = medicineAdapter
        binding.rcvMedicine.layoutManager = LinearLayoutManager(requireContext())
        medicineAdapter!!.setData(medicineList)


        labAdapter = PrescriptionAdapter(requireContext(),getString(R.string.add_test_title),this)
        binding.rcvTest.adapter = labAdapter
        binding.rcvTest.layoutManager = LinearLayoutManager(requireContext())
        labAdapter!!.setData(labTestList)


        prescriptionViewModel.prescription.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading->{
                    (activity as MainActivity).showLoading()
                }

                is Resource.Success->{
                    (activity as MainActivity).hideLoading()
                    if(it.data!!.id>0){
                        Toast.makeText(requireContext(),"Prescription created", Toast.LENGTH_SHORT).show()
                        updateConsultation(it.data.id)

                    }

                }
                is Resource.Error->{
                    (activity as MainActivity).hideLoading()
                    if(it.message== UtilConstants.unauthorized){
                        MedWizUtils.performLogout(requireContext(),requireActivity())
                    }

                }
            }
        })
        consultationViewModel.consultation.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading->{
                    (activity as MainActivity).showLoading()
                }

                is Resource.Success->{
                    (activity as MainActivity).hideLoading()
                    if(it.data!!.id>0){
                        Toast.makeText(requireContext(),"consultation updated successfully", Toast.LENGTH_SHORT).show()
                        goToHomeScreen()

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

    private fun goToHomeScreen() {
        findNavController().navigate(R.id.action_fragmentSendPrescription_to_homeFragment)
    }
    
    private fun createPrescription(){

        val jsonObject=JsonObject()
        jsonObject.addProperty("addedDate",MedWizUtils.getCurrentDate())
        jsonObject.addProperty("isActive",true)
        jsonObject.addProperty("updateDate",MedWizUtils.getCurrentDate())
        jsonObject.addProperty("patientId",consultation!!.patientId)
        jsonObject.addProperty("patientName",consultation!!.patientName)
        jsonObject.addProperty("docId",consultation!!.docId)
        jsonObject.addProperty("specialization",consultation!!.specialization)
        jsonObject.addProperty("age",consultation!!.age)
        jsonObject.addProperty("weight",0)
        jsonObject.addProperty("gender",userDetails!!.gender)
        val docName=userDetails!!.firstname+" "+userDetails!!.lastname
        jsonObject.addProperty("docName",docName)
        val medicationArray= JsonArray()
        for (i in 0 until medicineList.size) {
            val medicineObj=JsonObject()
            medicineObj.addProperty("dosage",medicineList[i].dosage)
            medicineObj.addProperty("name",medicineList[i].name)
            medicationArray.add(medicineObj)
        }
        val medicationLabArray= JsonArray()
        for (i in 0 until labTestList.size) {
            val medicineLabObj=JsonObject()
            medicineLabObj.addProperty("name",labTestList[i].name)
            medicationLabArray.add(medicineLabObj)
        }
        jsonObject.add("medicationLabs",medicationLabArray)

        jsonObject.add("medications",medicationArray)
        token=MedWizUtils.storeValueInPreference(requireContext(),UtilConstants.accessToken,"",false)
        prescriptionViewModel.createPrescription(token,jsonObject)

    }

    private fun updateConsultation(prescriptionId:Long) {
        val requestObj=JsonObject()
        requestObj.addProperty("addedDate",consultation!!.addedDate)
        requestObj.addProperty("consDate",consultation!!.consDate)
        requestObj.addProperty("consTime",consultation!!.consTime)
        requestObj.addProperty("docId",consultation!!.docId)
        requestObj.addProperty("fees",consultation!!.fees)
        requestObj.addProperty("filePath",consultation!!.filePath)
        requestObj.addProperty("isActive",consultation!!.isActive)
        requestObj.addProperty("isCash",consultation!!.isCash)
        requestObj.addProperty("laboratoryId",consultation!!.laboratoryId)
        requestObj.addProperty("pharmaId",consultation!!.pharmaId)
        requestObj.addProperty("status",UtilConstants.STATUS_COMPLETED)
        requestObj.addProperty("transactionId",consultation!!.transactionId)
        requestObj.addProperty("patientId",consultation!!.patientId)
        requestObj.addProperty("patientMobile",consultation!!.patientMobile)
        requestObj.addProperty("patientGender",consultation!!.patientGender)
        requestObj.addProperty("patientName",consultation!!.patientName)
        requestObj.addProperty("prescriptionId",prescriptionId)
        requestObj.addProperty("age",consultation!!.age)
        consultationViewModel.update(token,requestObj,consultation!!.id)

    }

    private fun setUi() {
        binding.includePatient.tvPatientName.text=consultation!!.patientName
        binding.includePatient.tvPatientGenderAndMobile.text= consultation!!.patientGender+" | "+consultation!!.patientMobile
        binding.includePatient.tvAge.text="Age:"+consultation!!.age
        binding.includePatient.tvDayAndDate.text=consultation!!.consDate
        binding.includePatient.tvTime.text=consultation!!.consTime
    }

    private fun goBack(){
        findNavController().navigateUp()
    }

    override fun onClickDosage(obj: Medication, position: Int) {
        MedWizUtils.showErrorPopup(requireContext(),obj.dosage)
    }


}