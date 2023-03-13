package com.medwiz.medwizdoctor.ui.clinic

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.TimePicker
import androidx.compose.ui.text.toLowerCase
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.medwiz.medwiz.model.ProfileItemModel
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.AddTimingLayoutBinding
import com.medwiz.medwizdoctor.databinding.FragmentAddClinicDetailsBinding
import com.medwiz.medwizdoctor.model.ClinicTiming
import com.medwiz.medwizdoctor.ui.main.MainActivity
import com.medwiz.medwizdoctor.util.MedWizUtils
import com.medwiz.medwizdoctor.util.UtilConstants
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import java.util.*


@AndroidEntryPoint
class ClinicDetailsFragment : Fragment(R.layout.fragment_add_clinic_details) {
    private lateinit var binding: FragmentAddClinicDetailsBinding
    private var adapter: ClinicTimingAdapter? = null
    private var clinicTimingList: ArrayList<ClinicTiming> = ArrayList<ClinicTiming>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddClinicDetailsBinding.bind(view)

        binding.tvBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btSave.setOnClickListener {
            uploadClinicData()
        }

        binding.btAddTiming.setOnClickListener {
            showDialog()
        }
        val profileItem1 =
            ProfileItemModel(UtilConstants.ITEM_PROFILE, "Notification", R.drawable.ic_notification)
        val profileItem2 = ProfileItemModel(
            UtilConstants.ITEM_EDIT_PROFILE,
            "Privacy & Policy",
            R.drawable.ic_privacy
        )
        val profileItem3 =
            ProfileItemModel(UtilConstants.ITEM_SETTING, "Help Center", R.drawable.ic_help_center)
        val lis = ArrayList<ProfileItemModel>()
        lis.add(profileItem1)
        lis.add(profileItem2)
        lis.add(profileItem3)
        adapter = ClinicTimingAdapter(requireActivity(), lis)
        binding.rcvClinicTiming.adapter = adapter
        binding.rcvClinicTiming.layoutManager = LinearLayoutManager(requireActivity())

    }

    private fun uploadClinicData() {
        val mainJsonObj = JsonObject()
        mainJsonObj.addProperty("address1", binding.etAddress.text.toString().trim())
        mainJsonObj.addProperty("city", binding.etCity.text.toString().trim())
        mainJsonObj.addProperty("state", binding.etState.text.toString().trim())
        mainJsonObj.addProperty("zip", binding.etPinCode.text.toString().trim())
        mainJsonObj.addProperty("country", "India")
        val docJsonObj = JsonObject()
        docJsonObj.addProperty("id", (activity as MainActivity).getUserDetails().user.id)

        //addingPeriod
        val periodJsonArray = JsonArray()
        for (timing in clinicTimingList) {


            val periodTimingJsonArray = JsonArray()
            val periodTimingJsonObj = JsonObject()

            periodTimingJsonObj.addProperty("dayOfVisit", timing.day)

            if (timing.morningStartTime != "Select") {
                val morningJsonObj = JsonObject()
                morningJsonObj.addProperty("periodName", "MORNING")
                morningJsonObj.addProperty("startTime", timing.morningStartTime)
                morningJsonObj.addProperty("endTime", timing.morningEndTime)
                periodTimingJsonArray.add(morningJsonObj)
            }

            if (timing.afterNoonStartTime != "Select") {
                val afterNoonJsonObj = JsonObject()
                afterNoonJsonObj.addProperty("periodName", "AFTERNOON")
                afterNoonJsonObj.addProperty("startTime", timing.afterNoonStartTime)
                afterNoonJsonObj.addProperty("endTime", timing.afterNoonEndTime)
                periodTimingJsonArray.add(afterNoonJsonObj)
            }

            if (timing.eveningStartTime != "Select") {
                val eveningJsonObj = JsonObject()
                eveningJsonObj.addProperty("periodName", "EVENING")
                eveningJsonObj.addProperty("startTime", timing.eveningStartTime)
                eveningJsonObj.addProperty("endTime", timing.eveningEndTime)
                periodTimingJsonArray.add(eveningJsonObj)
            }

            periodTimingJsonObj.add("periodTimings", periodTimingJsonArray)

            periodJsonArray.add(periodTimingJsonObj)
        }
        mainJsonObj.add("doctor", docJsonObj)
        mainJsonObj.add("periods", periodJsonArray)

    }

    private fun showDialog() {

        if (clinicTimingList.size >= 7) {
            return
        }
        var strTime = ""
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.add_timing_layout)
        val dialogBinding = AddTimingLayoutBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(dialogBinding.root)
        var strDay = "Select Day"
        val dayList = arrayOf(
            "Select Day",
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday"
        )
        val aa = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, dayList)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        dialogBinding.spinnerDay.adapter = aa

        dialogBinding.spinnerDay.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    if (position > 0) {
                        strDay = dayList[position]
                    }

                }

            }

        var textView: TextView? = null

        val mTimePicker: TimePickerDialog
        val mCurrentTime = Calendar.getInstance()
        val hour = mCurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mCurrentTime.get(Calendar.MINUTE)

        mTimePicker =
            TimePickerDialog(requireContext(), object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    strTime = MedWizUtils.getTime(hourOfDay, minute)
                        .toString()//String.format("%d : %d", hourOfDay, minute)
                    if (textView != null) {
                        textView!!.setText(strTime)
                    }
                }
            }, hour, minute, false)



        dialogBinding.tvMoStartTime.setOnClickListener {
            mTimePicker.cancel()
            strTime = ""
            mTimePicker.show()
            textView = dialogBinding.tvMoStartTime
        }
        dialogBinding.tvMoEndTime.setOnClickListener {
            mTimePicker.cancel()
            strTime = ""
            mTimePicker.show()
            textView = dialogBinding.tvMoEndTime
        }
        dialogBinding.tvAfStartTime.setOnClickListener {
            mTimePicker.cancel()
            strTime = ""
            mTimePicker.show()
            textView = dialogBinding.tvAfStartTime
        }
        dialogBinding.tvAfEndTime.setOnClickListener {
            mTimePicker.cancel()
            strTime = ""
            mTimePicker.show()
            textView = dialogBinding.tvAfEndTime
        }
        dialogBinding.tvEvStartTime.setOnClickListener {
            mTimePicker.cancel()
            strTime = ""
            mTimePicker.show()
            textView = dialogBinding.tvEvStartTime
        }
        dialogBinding.tvEvEndTime.setOnClickListener {
            mTimePicker.cancel()
            strTime = ""
            mTimePicker.show()
            textView = dialogBinding.tvEvEndTime
        }
        var morningStart = "NA"
        var morningEnd = "NA"
        var afterNoonStart = "NA"
        var afterNoonEnd = "NA"
        var eveningStart = "NA"
        var eveningEnd = "NA"
        dialogBinding.btAdd.setOnClickListener {

            if (strDay.isEmpty() || strDay == "Select Day") {
                MedWizUtils.showToast("Please Select day", requireContext())
                return@setOnClickListener
            }
            //Morning
            morningStart = dialogBinding.tvMoStartTime.text.toString()
            morningEnd = dialogBinding.tvMoEndTime.text.toString()

            //Afternoon
            afterNoonStart = dialogBinding.tvAfStartTime.text.toString()
            afterNoonEnd = dialogBinding.tvAfEndTime.text.toString()

            //evening
            eveningStart = dialogBinding.tvEvStartTime.text.toString()
            eveningEnd = dialogBinding.tvEvEndTime.text.toString()

            val clinicTiming = ClinicTiming(
                strDay, morningStart, morningEnd,
                afterNoonStart, afterNoonEnd, eveningStart, eveningEnd
            )
            clinicTimingList.add(clinicTiming)
            strDay = "Select Day"
            dialog.dismiss()
        }

        dialog.show()

//        val layoutParams = WindowManager.LayoutParams()
//        layoutParams.copyFrom(dialog.getWindow()!!.getAttributes())
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
//        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
//        dialog.getWindow()!!.setAttributes(layoutParams)

    }


}