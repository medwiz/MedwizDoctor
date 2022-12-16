package com.medwiz.medwizdoctor.ui.prescription

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.medwiz.medwiz.doctorsView.docotorUi.consult.PrescriptionAdapter
import com.medwiz.medwiz.doctorsView.docotorUi.consult.PrescriptionListener
import com.medwiz.medwiz.model.Medication
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentPreviewBinding
import com.medwiz.medwizdoctor.ui.main.MainActivity
import com.medwiz.medwizdoctor.util.MedWizUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewPrescriptionFragment:Fragment(R.layout.fragment_preview), PrescriptionListener {

    private lateinit var binding: FragmentPreviewBinding
    private var medicineAdapter: PrescriptionAdapter?=null
    private var labAdapter: PrescriptionAdapter?=null
    private var medicineList=ArrayList<Medication>()
    private var labTestList=ArrayList<Medication>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentPreviewBinding.bind(view)
        medicineList =  (activity as MainActivity).getMedicineList()
        labTestList= (activity as MainActivity).getTestList()
        createMedicineAdapter()
        createLabAdapter()
        binding.imgBack.setOnClickListener {
            goBack()
        }



    }

    private fun goBack(){
        findNavController().navigateUp()
    }

    private fun createMedicineAdapter(){
        medicineAdapter = PrescriptionAdapter(requireContext(),getString(R.string.add_medicine_title),this)
        binding.rcvMedicine.adapter = medicineAdapter
        binding.rcvMedicine.layoutManager = LinearLayoutManager(requireContext())
        medicineAdapter!!.setData(medicineList)

    }

    private fun createLabAdapter(){
        labAdapter = PrescriptionAdapter(requireContext(),getString(R.string.add_test_title),this)
        binding.rcvTest.adapter = labAdapter
        binding.rcvTest.layoutManager = LinearLayoutManager(requireContext())
        labAdapter!!.setData(labTestList)
    }

    override fun onClickDosage(obj: Medication, position: Int) {
        MedWizUtils.showErrorPopup(requireContext(),obj.dosage)
    }
}