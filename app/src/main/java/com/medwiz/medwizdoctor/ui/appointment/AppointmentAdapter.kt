package com.medwiz.medwizdoctor.ui.appointment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.databinding.SinglePatientRcvItemBinding
import com.medwiz.medwizdoctor.util.UtilConstants

/**
 * @Author: Prithwiraj Nath
 * @Date:24/01/23
 */
class AppointmentAdapter(
    private val context: Context,
    private val listener: AppointmentListener
) : RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {
    private var consultationList: ArrayList<String> = ArrayList()
    private var type: String = "UPCOMING"


    public fun setData(list: ArrayList<String>, bookingType: String) {
        this.consultationList = list
        this.type = bookingType
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val binding =
            SinglePatientRcvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return AppointmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val itemObj = consultationList[position]
        holder.bind(itemObj, position)
    }

    override fun getItemCount(): Int {
        return consultationList.size
    }

    inner class AppointmentViewHolder(val binding: SinglePatientRcvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemObj: String, position: Int) {
            binding.nameTextView.text=itemObj
            when (type) {
                UtilConstants.TYPE_UPCOMING -> {
                    binding.btTagName.text = "UPCOMING"
                    binding.btTagName.background =
                        context.resources.getDrawable(R.drawable.ic_upcoming_button);

                }
                UtilConstants.TYPE_COMPLETED -> {
                    binding.btTagName.text = "COMPLETED"
                    binding.btTagName.background =
                        context.resources.getDrawable(R.drawable.ic_completed_button);
                }
                UtilConstants.TYPE_CANCELLED -> {
                    binding.btTagName.text = "CANCELED"
                    binding.btTagName.background =
                        context.resources.getDrawable(R.drawable.ic_cancelled_button);
                }

            }
//            binding.nameTextView.text = doctorItem.firstname+" "+doctorItem.lastname
//            binding.tvSpecialization.text=doctorItem.specialization

        }
    }

}