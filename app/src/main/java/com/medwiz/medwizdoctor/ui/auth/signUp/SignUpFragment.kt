package com.medwiz.medwizdoctor.ui.auth.signUp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.medwiz.medwiz.model.RegisterRequest
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentSignUpBinding
import com.medwiz.medwizdoctor.util.UtilConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment:Fragment(R.layout.fragment_sign_up) {
    private lateinit var binding: FragmentSignUpBinding
    private var accountType:String= UtilConstants.doctor
    var genderList = arrayOf("Select Gender","Male", "Female", "Others")
    private var strGender:String="Male"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)
        binding.imgBackCreateAccount.setOnClickListener{

            findNavController().navigateUp()


        }
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

        binding.btNextStep.setOnClickListener {
            val firstName=binding.etFName.text.toString()
            val lastName=binding.etLName.text.toString()
            val mobile=binding.etPhoneNumber.text.toString()
            val email=binding.etMail.text.toString()
            val pinCode=binding.etPincode.text.toString()
            val age=binding.etAge.text.toString()
             val gender=strGender
            if(firstName.isNotEmpty()&&lastName.isNotEmpty()&&mobile.isNotEmpty()&&email.isNotEmpty()&&pinCode.isNotEmpty()&&gender.isNotEmpty()){
                val register= RegisterRequest()
                register.firstname=firstName
                register.lastname=lastName
                register.mobile=mobile
                register.email=email
                register.pinCode=pinCode
                register.userType=accountType
                register.age=age
                register.gender=gender
                val bundle = Bundle()
                bundle.putParcelable(UtilConstants.request,register)



            }

        }
        binding.liLogin.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}