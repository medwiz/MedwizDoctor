package com.medwiz.medwizdoctor.ui.history

import com.medwiz.medwizdoctor.model.EyeExamination

/**
 * @Author: Prithwiraj Nath
 * @Date:05/03/23
 */
interface EyeExamListener {
    fun onItemAdd(obj:EyeExamination,position:Int)
}