package com.medwiz.medwizdoctor.ui.prescription

import com.medwiz.medwizdoctor.model.MedicineResponse

/**
 * @Author: Prithwiraj Nath
 * @Date:11/12/22
 */
interface OnSearchItemListener {

    fun onItemClick(obj: MedicineResponse, position:Int)
}