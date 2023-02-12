package com.medwiz.medwizdoctor.ui.auth.login
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.medwiz.medwizdoctor.databinding.EnterOtpBottomsheetBinding
import com.medwiz.medwizdoctor.ui.main.MainActivity
import android.view.LayoutInflater as LayoutInflater1

/**
 * @Author: Prithwiraj Nath
 * @Date:30/01/23
 */
class EnterOtpBottomSheet: BottomSheetDialogFragment() {
    private lateinit var binding: EnterOtpBottomsheetBinding
    private var pin=""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        binding.btContinue.setOnClickListener {
            if(pin.isNotEmpty()&&pin.length==4){
                (activity as MainActivity).bottomSheetViewModel.pin.value=pin
                 dismiss()
            }else{
                Toast.makeText(activity,"Please enter valid otp",Toast.LENGTH_SHORT).show()
            }

        }
        binding.imgCancel.setOnClickListener {
            dismiss()
        }


        binding.etPassCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                pin=s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s?.length==4){
                   pin=s.toString()
                }else{

                }

            }
        })
    }




    override fun onCreateView(inflater: LayoutInflater1, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = EnterOtpBottomsheetBinding.inflate(inflater,container,false)
        setStyle(STYLE_NORMAL, com.google.android.material.R.style.Animation_Design_BottomSheetDialog);
        return binding.root

    }
}