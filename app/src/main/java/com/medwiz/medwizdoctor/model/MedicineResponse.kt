package com.medwiz.medwizdoctor.model

/**
 * @Author: Prithwiraj Nath
 * @Date:11/12/22
 */
data class MedicineResponse(
    val name:String,
    val brand:String,
    val price:Double,
    val isActive:Boolean,
    val dosage:String,
    val type:String
)
