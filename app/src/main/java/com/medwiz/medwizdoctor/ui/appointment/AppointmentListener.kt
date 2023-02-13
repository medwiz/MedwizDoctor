package com.medwiz.medwizdoctor.ui.appointment

import com.medwiz.medwiz.model.Consultation

/**
 * @Author: Prithwiraj Nath
 * @Date:24/01/23
 */
interface AppointmentListener {
    fun onClickItem(obj:Consultation,position:Int)
}