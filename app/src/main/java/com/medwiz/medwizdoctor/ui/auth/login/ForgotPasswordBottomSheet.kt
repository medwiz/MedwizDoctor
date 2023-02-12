package com.medwiz.medwizdoctor.ui.auth.login
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.medwiz.medwizdoctor.databinding.ForgotPasswordBottomsheetBinding
import com.medwiz.medwizdoctor.ui.main.MainActivity
import android.view.LayoutInflater as LayoutInflater1

/**
 * @Author: Prithwiraj Nath
 * @Date:30/01/23
 */
class ForgotPasswordBottomSheet: BottomSheetDialogFragment() {
    private lateinit var binding: ForgotPasswordBottomsheetBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        binding.btContinue.setOnClickListener {
              val phoneNumber=binding.etUserPhoneNumber.text.toString()
            if(phoneNumber.isNotEmpty()){
                (activity as MainActivity).bottomSheetViewModel.phoneNumber.value=phoneNumber
                 dismiss()
            }else{
                Toast.makeText(activity,"Please enter valid phone number",Toast.LENGTH_SHORT).show()
            }

        }
        binding.imgCancel.setOnClickListener {
            dismiss()
        }
    }




    override fun onCreateView(inflater: LayoutInflater1, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ForgotPasswordBottomsheetBinding.inflate(inflater,container,false)
        setStyle(STYLE_NORMAL, com.google.android.material.R.style.Animation_Design_BottomSheetDialog);
        return binding.root

    }
}