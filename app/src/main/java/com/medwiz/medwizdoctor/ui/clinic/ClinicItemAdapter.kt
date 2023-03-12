package com.medwiz.medwizdoctor.ui.clinic
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.medwiz.medwiz.model.ProfileItemModel
import com.medwiz.medwizdoctor.databinding.ClinicSingleItemBinding
import com.medwiz.medwizdoctor.databinding.SingleProfileItemBinding

/**
 * @Author: Prithwiraj Nath
 * @Date:23/01/23
 */



class ClinicItemAdapter (private val context: Context,
                         private val itemList:MutableList<ProfileItemModel>,
): RecyclerView.Adapter<ClinicItemAdapter.ClinicItemItemViewHolder>(){





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicItemItemViewHolder {
        val binding = ClinicSingleItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ClinicItemItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClinicItemItemViewHolder, position: Int) {
        val foodItem = itemList[position]
        holder.bind(foodItem,position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ClinicItemItemViewHolder(val binding: ClinicSingleItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(profileItem: ProfileItemModel, position: Int){
            binding.tvClinicName.text="Clinic"+(position+1)



        }
    }
}