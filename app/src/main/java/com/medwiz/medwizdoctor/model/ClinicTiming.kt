package com.medwiz.medwizdoctor.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @Author: Prithwiraj Nath
 * @Date:12/03/23
 */
@Parcelize
data class ClinicTiming(
    var day: String,
    var morningStartTime: String,
    var morningEndTime: String,
    var afterNoonStartTime: String,
    var afterNoonEndTime: String,
    var eveningStartTime: String,
    var eveningEndTime: String
): Parcelable {
    constructor() : this("","", "", "","","","")
}
