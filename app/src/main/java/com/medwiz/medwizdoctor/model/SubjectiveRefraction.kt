package com.medwiz.medwizdoctor.model

/**
 * @Author: Prithwiraj Nath
 * @Date:04/03/23
 */
data class SubjectiveRefraction(
    var eye: String,
    var sph: String,
    var cyl: String,
    var axis:String,
    var vision:String,
    var add:String,
    var nearVision:String
)
