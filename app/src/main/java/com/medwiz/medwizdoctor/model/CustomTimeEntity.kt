package com.medwiz.medwizdoctor.model

data class CustomTimeEntity(
    var time:String,
    var amOrPm:String,
    var isSelected:Boolean
){
    constructor() : this("", "",false)
}
