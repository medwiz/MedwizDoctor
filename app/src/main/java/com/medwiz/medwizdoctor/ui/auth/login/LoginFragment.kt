package com.medwiz.medwizdoctor.ui.auth.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.FragmentLoginBinding
import com.medwiz.medwizdoctor.model.LoginResponse
import com.medwiz.medwizdoctor.ui.main.MainActivity
import com.medwiz.medwizdoctor.util.MedWizUtils
import com.medwiz.medwizdoctor.util.Resource
import com.medwiz.medwizdoctor.util.UtilConstants
import com.medwiz.medwizdoctor.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by viewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        checkAccessToken()
        binding.showLoginPassBtn.setOnClickListener {
            showHidePass(binding.showLoginPassBtn, binding.etPassword)
        }
        binding.btLogin.setOnClickListener {
            val userPhoneNumber = binding.etUserPhoneNumber.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

                viewModel.login(userPhoneNumber, password)


        }
        binding.liSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)

        }
        binding.tvForgotPassword.setOnClickListener {
            ForgotPasswordBottomSheet().show(requireActivity().supportFragmentManager, "enterPhone")
        }

        (activity as MainActivity). bottomSheetViewModel.phoneNumber.observe(viewLifecycleOwner,Observer{
            if(it.isNotEmpty()){
            EnterOtpBottomSheet().show(requireActivity().supportFragmentManager,"enterOtp")
            }

        })

        (activity as MainActivity). bottomSheetViewModel.pin.observe(viewLifecycleOwner,Observer{
            //validate the pin
            if(it.isNotEmpty()){
                val pin=it
                //if pin is valid
                ResetPasswordBottomSheet().show(requireActivity().supportFragmentManager,"ResetPassword")
            }

        })
        (activity as MainActivity). bottomSheetViewModel.newPassword.observe(viewLifecycleOwner,Observer{
            //change password
            if(it.isNotEmpty()){
                val pin=it
            }

        })




        viewModel.login.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    (activity as MainActivity).showLoading()
                }

                is Resource.Success -> {
                    (activity as MainActivity).hideLoading()
                    var isRoleDoctor=false
                    for(i in it.data!!.user.roles){
                        if(i.roleName=="DOCTOR"){
                            isRoleDoctor=true
                        }
                    }
                    if(isRoleDoctor){
                        goToNextScreen(it.data)
                    }else{
                        MedWizUtils.showErrorPopup(requireActivity(),"Please contact our admin team")
                    }



                }
                is Resource.Error -> {
                    (activity as MainActivity).hideLoading()
//                    if(it.message==UtilConstants.unauthorized){
//                        MedWizUtils.showErrorPopup(
//                            requireActivity(),
//                            requireActivity().getString(R.string.INVALID_USER_CREDENTIALS))
//
//                    }
//                    else{
//                        MedWizUtils.showErrorPopup(
//                            requireActivity(),
//                            requireActivity().getString(R.string.something_went_wrong))
//                    }
                    MedWizUtils.showErrorPopup(
                        requireActivity(),
                        it.message.toString()
                    )
                }
            }
        })


        binding.etUserPhoneNumber.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length == 10) {
                    binding.imgTick.setImageResource(R.drawable.ic_tick_green)
                } else {
                    binding.imgTick.setImageResource(R.drawable.ic_tick)
                }
            }
        })


//        binding.etEmail.setText("s@gmail.com")
//        binding.etPassword.setText("s12345")
    }

    private fun checkAccessToken() {
        val token= MedWizUtils.storeValueInPreference(requireContext(),
            UtilConstants.accessToken,"",false)
        if(token.isNotEmpty()){
           goToHomeScreen()
        }
    }

    private fun goBack() {
        requireActivity().finish()
    }

    private fun showHidePass(view: View, editText: EditText) {
        if (editText.transformationMethod
                .equals(PasswordTransformationMethod.getInstance())
        ) {
            (view as ImageView).setImageResource(R.drawable.ic_show_password)
            //Show Password
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            (view as ImageView).setImageResource(R.drawable.ic_hide_password)
            //Hide Password
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
        }

    }

    private fun goToNextScreen(it: LoginResponse) {
        MedWizUtils.storeValueInPreference(
            requireContext(),
            UtilConstants.accessToken,
            "Bearer " + it.token,
            true
        )
        MedWizUtils.storeValueInPreference(
            requireContext(),
            UtilConstants.userId,
             it.user.id.toString(),
            true
        )

        goToHomeScreen()

    }

    private fun goToHomeScreen(){
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

}