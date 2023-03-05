package com.medwiz.medwizdoctor.ui.history

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentPatientDetailsBinding
import com.medwiz.medwizdoctor.databinding.FragmentPtientHistoryBinding
import com.medwiz.medwizdoctor.model.EyeExamination
import com.medwiz.medwizdoctor.model.SubjectiveRefraction

/**
 * @Author: Prithwiraj Nath
 * @Date:19/02/23
 */
class PatientHistoryFragment:Fragment(R.layout.fragment_ptient_history),EyeExamListener {
    private lateinit var binding:FragmentPtientHistoryBinding
    private var sRArrayList = ArrayList<SubjectiveRefraction>()
    private lateinit var tableRowAdapter: TableEyeRowAdapter
    var genderList = arrayOf("Select Gender","Male", "Female", "Others")
    private var strGender:String="Male"
    private lateinit var eyeExaminationAdapter:EyeExaminationAdapter
    private var eyeExamArrayList=java.util.ArrayList<EyeExamination>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentPtientHistoryBinding.bind(view)
        binding.tvBack.setOnClickListener {
            findNavController().navigateUp()
        }
        setGenderSpinner()

        sRArrayList.add(SubjectiveRefraction("R/E", "","","",
            "","",""))
        sRArrayList.add(SubjectiveRefraction("L/E", "","","",
            "","",""))

        tableRowAdapter = TableEyeRowAdapter(sRArrayList)

        binding.inEye.tableRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.inEye.tableRecyclerView.adapter = tableRowAdapter

        eyeExamArrayList.add(EyeExamination("EyeLid","",""))
        eyeExamArrayList.add(EyeExamination("Conjuctiva","",""))
        eyeExamArrayList.add(EyeExamination("Sclera","",""))
        eyeExamArrayList.add(EyeExamination("Cornea","",""))
        eyeExamArrayList.add(EyeExamination("Anterior Chamber","",""))
        eyeExamArrayList.add(EyeExamination("Iris","",""))
        eyeExamArrayList.add(EyeExamination("Pupil","",""))
        eyeExamArrayList.add(EyeExamination("Lens","",""))
        eyeExamArrayList.add(EyeExamination("Fundus","",""))
        eyeExamArrayList.add(EyeExamination("Lacrimal Sac","",""))

        eyeExaminationAdapter= EyeExaminationAdapter(requireContext(),this)

        binding.rcvEyeExam.layoutManager=LinearLayoutManager(requireContext())
        binding.rcvEyeExam.adapter=eyeExaminationAdapter
        eyeExaminationAdapter.setData(eyeExamArrayList)
    }

    private fun setGenderSpinner() {

    val aa = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderList)
    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    // Set Adapter to Spinner
    binding.spinnerGender.adapter = aa

    binding.spinnerGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            if(position>0){
                strGender=genderList[position]
            }

        }

    }
    }

    override fun onItemAdd(obj:EyeExamination,position: Int) {
        val i=0
    }


}