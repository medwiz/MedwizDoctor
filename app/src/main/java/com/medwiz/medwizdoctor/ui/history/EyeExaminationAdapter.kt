package com.medwiz.medwizdoctor.ui.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.SingleEyeExamLayoutBinding
import com.medwiz.medwizdoctor.databinding.SinglePatientRcvItemBinding
import com.medwiz.medwizdoctor.model.EyeExamination
import com.medwiz.medwizdoctor.util.UtilConstants

/**
 * @Author: Prithwiraj Nath
 * @Date:24/01/23
 */
class EyeExaminationAdapter(
    private val context: Context,
    private val listener: EyeExamListener
) : RecyclerView.Adapter<EyeExaminationAdapter.EyeExaminationViewHolder>() {
    private var eyeExaminationList: ArrayList<EyeExamination> = ArrayList()


    public fun setData(list: ArrayList<EyeExamination>) {
        this.eyeExaminationList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EyeExaminationViewHolder {
        val binding =
            SingleEyeExamLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return EyeExaminationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EyeExaminationViewHolder, position: Int) {
        val itemObj = eyeExaminationList[position]
        holder.bind(itemObj, position)
    }

    override fun getItemCount(): Int {
        return eyeExaminationList.size
    }

    inner class EyeExaminationViewHolder(val binding: SingleEyeExamLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemObj: EyeExamination, position: Int) {
            binding.tvEyeTestName.text=itemObj.testName
            binding.etRightEyeValue.setText(itemObj.rightEyeValue)
            binding.etLeftEyeValue.setText(itemObj.leftEyeValue)


            binding.root.setOnClickListener {
                listener.onItemAdd(itemObj,position)
            }

        }
    }

}