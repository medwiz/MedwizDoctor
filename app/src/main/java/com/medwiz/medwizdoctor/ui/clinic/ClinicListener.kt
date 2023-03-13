package com.medwiz.medwizdoctor.ui.clinic

import com.medwiz.medwizdoctor.model.Clinic
import java.text.FieldPosition

/**
 * @Author: Prithwiraj Nath
 * @Date:13/03/23
 */
interface ClinicListener {
    fun onSelectClinic(obj:Any,position: Int)
}