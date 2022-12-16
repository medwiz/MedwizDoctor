package com.medwiz.medwiz.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DoctorInfo(
    var experience: String,
    var specialization: String,
    var licencePath:String,
    var about:String,
    var fees:Int
): Parcelable{
    constructor() : this("", "","","",0)
}
