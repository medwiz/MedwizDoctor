package com.medwiz.medwizdoctor.ui.auth.login
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.ResetPasswordBottomsheetBinding
import com.medwiz.medwizdoctor.ui.main.MainActivity
import android.view.LayoutInflater as LayoutInflater1

/**
 * @Author: Prithwiraj Nath
 * @Date:30/01/23
 */
class ResetPasswordBottomSheet: BottomSheetDialogFragment() {
    private lateinit var binding: ResetPasswordBottomsheetBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        binding.btContinue.setOnClickListener {
              val newPassword=binding.etNewPassword.text.toString()
            val confirmPassword=binding.etConfirmPassword.text.toString()
            if(newPassword.isNotEmpty()&&confirmPassword.isNotEmpty()&&newPassword==confirmPassword){
                (activity as MainActivity).bottomSheetViewModel.newPassword.value=newPassword
                 dismiss()
            }else{
                Toast.makeText(activity,"Password did not match",Toast.LENGTH_SHORT).show()
            }

        }
        binding.imgCancel.setOnClickListener {
            dismiss()
        }

        binding.showNewPassBtn.setOnClickListener {
            showHidePass(binding.showNewPassBtn, binding.etNewPassword)
        }
        binding.showConfirmPassBtn.setOnClickListener {
            showHidePass(binding.showConfirmPassBtn, binding.etConfirmPassword)
        }
    }




    override fun onCreateView(inflater: LayoutInflater1, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ResetPasswordBottomsheetBinding.inflate(inflater,container,false)
        setStyle(STYLE_NORMAL, com.google.android.material.R.style.Animation_Design_BottomSheetDialog);
        return binding.root

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
}