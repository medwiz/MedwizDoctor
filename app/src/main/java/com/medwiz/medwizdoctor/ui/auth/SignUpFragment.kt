package com.medwiz.medwizdoctor.ui.auth
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.medwiz.medwiz.model.RegisterRequest
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentSignupBinding
import com.medwiz.medwizdoctor.util.UtilConstants
import com.medwiz.medwizdoctor.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment :Fragment(R.layout.fragment_signup){
    private lateinit var binding: FragmentSignupBinding
    private val viewModel: AuthViewModel by viewModels()
    private var isComingFromCreatePassword:Boolean=false
    var genderList = arrayOf("Select Gender","Male", "Female", "Others")
    private var strGender:String="Male"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignupBinding.bind(view)
        isComingFromCreatePassword = requireArguments().getBoolean(UtilConstants.SCREEN_NAME, false)
        binding.imgBackCreateAccount.setOnClickListener{

            findNavController().navigateUp()


        }
        binding.etFName.setText("Dr.")
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
                register.userType=UtilConstants.ACCOUNT_DOCTOR
                register.age=age
                register.gender=gender
                val bundle = Bundle()
                bundle.putParcelable(UtilConstants.request,register)
                findNavController().navigate(R.id.action_signUpFragment_to_addDocInfoFragment,bundle)
            }

        }
        binding.liLogin.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}