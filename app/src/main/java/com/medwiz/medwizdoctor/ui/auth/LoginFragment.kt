package com.medwiz.medwizdoctor.ui.auth
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
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
class LoginFragment :Fragment(R.layout.fragment_login){
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    private var isComingFromCreatePassword:Boolean=false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
       // isComingFromCreatePassword= requireArguments().getBoolean(UtilConstants.SCREEN_NAME,false)
        binding.showLoginPassBtn.setOnClickListener {
            showHidePass(binding.showLoginPassBtn,binding.etPassword)
        }
        binding.btContinue.setOnClickListener {


            val username =  binding.etUserName.text.toString().trim()
            val password =  binding.etPassword.text.toString().trim()

            viewModel.login(username,password)


        }
        binding.liSignUp.setOnClickListener {
            val bundle=Bundle()
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment,bundle)

        }

        viewModel.login.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading->{
                    (activity as MainActivity).showLoading()
                }

                is Resource.Success->{
                    (activity as MainActivity).hideLoading()
                    goToNextScreen(it.data!!)
                }
                is Resource.Error->{
                    (activity as MainActivity).hideLoading()
                    MedWizUtils.showErrorPopup(
                        requireActivity(),
                        it.message.toString())
                }
            }
        })





//        binding.etEmail.setText("s@gmail.com")
//        binding.etPassword.setText("s12345")
    }

    private fun goBack() {
        requireActivity().finish()
    }
    private fun showHidePass(view: View,editText: EditText) {
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
        MedWizUtils.storeValueInPreference(requireContext(),UtilConstants.accessToken,"Bearer "+it.token,true)
        MedWizUtils.storeValueInPreference(requireContext(),UtilConstants.userId,it.id.toString(),true)
        MedWizUtils.storeValueInPreference(requireContext(),UtilConstants.email,it.email,true)
        MedWizUtils.storeValueInPreference(requireContext(),UtilConstants.userType,it.userType,true)
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

    }
}