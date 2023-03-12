package com.medwiz.medwizdoctor.ui.clinic
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medwiz.medwiz.model.ProfileItemModel
import com.medwiz.medwizdoctor.databinding.ClinicSingleItemBinding
import com.medwiz.medwizdoctor.databinding.SingleClinicTimingBinding
import com.medwiz.medwizdoctor.databinding.SingleProfileItemBinding

/**
 * @Author: Prithwiraj Nath
 * @Date:23/01/23
 */



class ClinicTimingAdapter (private val context: Context,
                           private val itemList:MutableList<ProfileItemModel>,
): RecyclerView.Adapter<ClinicTimingAdapter.ClinicTimingViewHolder>(){





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicTimingViewHolder {
        val binding = SingleClinicTimingBinding.inflate(LayoutInflater.from(context),parent,false)
        return ClinicTimingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClinicTimingViewHolder, position: Int) {
        val foodItem = itemList[position]
        holder.bind(foodItem,position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ClinicTimingViewHolder(val binding: SingleClinicTimingBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(profileItem: ProfileItemModel, position: Int){

        }
    }
}