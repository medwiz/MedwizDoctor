package com.medwiz.medwizdoctor.ui.home

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medwiz.medwiz.model.Consultation
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.SinglePatientRcvItemBinding
import com.medwiz.medwizdoctor.util.UtilConstants

import java.util.ArrayList

class PatientAdapter(private val context: Context,
                     private val type:String,
                     private val listener: ConsultationListener
):RecyclerView.Adapter<PatientAdapter.PatientViewHolder>(){

    private var itemList:ArrayList<Consultation> = ArrayList()

    public fun setData(list:ArrayList<Consultation>){
        this.itemList=list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val binding = SinglePatientRcvItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return PatientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val foodItem = itemList[position]
        holder.bind(foodItem,position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class PatientViewHolder(val binding: SinglePatientRcvItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(doctorItem: Consultation, position: Int){
            binding.tvPatientName.text = doctorItem.patientName
            binding.tvPatientGenderAndMobile.text= doctorItem.patientGender+" | "+doctorItem.patientMobile
            binding.tvDayAndDate.text=doctorItem.consDate
            binding.tvAge.text="Age: "+doctorItem.age
            binding.tvTime.text=doctorItem.consTime
            binding.layMain.setOnClickListener {
                listener.onClickConsult(position,doctorItem)
            }
            when(type){
                UtilConstants.TYPE_UPCOMING->{
                    binding.imgStatus.visibility= View.GONE
                }
                UtilConstants.TYPE_COMPLETED->{
                    binding.imgStatus.setImageResource(R.drawable.ic_completed_appointment)
                    binding.imgStatus.visibility= View.VISIBLE
                }
                UtilConstants.TYPE_CANCELLED->{
                    binding.imgStatus.visibility= View.VISIBLE
                    binding.imgStatus.setImageResource(R.drawable.ic_cancelled_appointment)
                }
            }
        }
    }
}