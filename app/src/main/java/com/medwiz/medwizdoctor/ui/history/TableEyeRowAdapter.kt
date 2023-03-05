package com.medwiz.medwizdoctor.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.medwiz.medwizdoctor.R
import com.medwiz.medwizdoctor.model.SubjectiveRefraction

/**
 * @Author: Prithwiraj Nath
 * @Date:04/03/23
 */
class TableEyeRowAdapter (private var sRArrayList: ArrayList<SubjectiveRefraction>) :
RecyclerView.Adapter<TableEyeRowAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.eye_table_row_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.tvEye.text = sRArrayList[i].eye
        viewHolder.etSph.setText(sRArrayList[i].sph)
        viewHolder.etCyl.setText(sRArrayList[i].cyl)
        viewHolder.etAxis.setText(sRArrayList[i].axis)
        viewHolder.etVision.setText( sRArrayList[i].vision)
        viewHolder.etAdd.setText(sRArrayList[i].add)
        viewHolder.etNearVn.setText (sRArrayList[i].nearVision)
    }

    override fun getItemCount(): Int {
        return sRArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEye: TextView = itemView.findViewById(R.id.tvEye)
        val etSph: EditText = itemView.findViewById(R.id.etSph)
        val etCyl: EditText = itemView.findViewById(R.id.etCyl)

        val etAxis: EditText = itemView.findViewById(R.id.etAxis)
        val etVision: EditText = itemView.findViewById(R.id.etVision)
        val etAdd: EditText = itemView.findViewById(R.id.etAdd)
        val etNearVn: EditText = itemView.findViewById(R.id.etNearVn)


    }
}