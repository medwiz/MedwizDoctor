package com.medwiz.medwizdoctor.ui.auth.signUp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.medwiz.medwizdoctor.model.RegisterRequest
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentSignUpBinding
import com.medwiz.medwizdoctor.ui.main.MainActivity
import com.medwiz.medwizdoctor.util.MedWizUtils
import com.medwiz.medwizdoctor.util.Resource
import com.medwiz.medwizdoctor.util.UtilConstants
import com.medwiz.medwizdoctor.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment:Fragment(R.layout.fragment_sign_up) {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: AuthViewModel by viewModels()
    private var accountType:String= UtilConstants.doctor
    var genderList = arrayOf("SELECT GENDER","MALE", "FEMALE", "OTHERS")
    private var strGender:String="MALE"
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
            val pinCode=binding.etPinCode.text.toString()
            val password=binding.etConfirmPassword.text.toString()
            val age=binding.etAge.text.toString()
            val address=binding.etAddress.text.toString()
            val state=binding.etState.text.toString()
            val city=binding.etCity.text.toString()
            val gender=strGender
            if(password!=binding.etConfirmPassword.text.toString()){
                MedWizUtils.showErrorPopup(requireActivity(),"Confirm Password not matching")
                return@setOnClickListener
            }
            if(firstName.isNotEmpty()&&lastName.isNotEmpty()&&mobile.isNotEmpty()&&mobile.length==10&&pinCode.isNotEmpty()&&gender.isNotEmpty()&&password.isNotEmpty()
                &&address.isNotEmpty()&&state.isNotEmpty()&&city.isNotEmpty()){
                val register= RegisterRequest()
                register.firstname=firstName
                register.lastname=lastName
                register.mobile=mobile
                register.email=email
                register.address=address
                register.state=state
                register.city=city
                register.pinCode=pinCode
                register.userType=accountType
                register.age=age
                register.gender=gender
                register.password=password
                viewModel.signUp(register)
            }else{
                MedWizUtils.showErrorPopup(requireActivity(),"Please Enter all Details carefully,Email is Optional!")
                return@setOnClickListener
            }

            viewModel.register.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Resource.Loading -> {
                        (activity as MainActivity).showLoading()
                    }

                    is Resource.Success -> {
                        (activity as MainActivity).hideLoading()
                        MedWizUtils.showToast("Account created successfully!",requireActivity())
                        goToLoginScreen()
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
        binding.liLogin.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun goToLoginScreen(){
        findNavController().navigateUp()
    }
}