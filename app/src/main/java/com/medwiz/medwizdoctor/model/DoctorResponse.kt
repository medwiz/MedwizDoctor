package com.medwiz.medwizdoctor.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.ArrayList

@Parcelize
data class DoctorResponse(
    val about: String,
    val activated: Boolean,
    val address: String,
    val age: Int,
    val credit: String,
    val email: String,
    val experience: String,
    val firstname: String,
    val id: Long,
    val lastname: String,
    val licencePath: String,
    val mobile: String,
    val pinCode: String,
    val fees:Int,
   // val reviews: ArrayList<Review>,
    val workingTimes:ArrayList<WorkTimings>,
    val specialization: String
):Parcelable

