package com.medwiz.medwizdoctor.model

/**
 * @Author: Prithwiraj Nath
 * @Date:07/01/23
 */
data class PatientResponse(
    val age: Int,
    val createdAt: String,
    val id: Int,
    val patientHistory: Any,
    val pinCode: Any,
    val profileImageUrl: Any,
    val updatedAt: String,
    val user: User,
    val weight: Double
)

