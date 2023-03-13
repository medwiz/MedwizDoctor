package com.medwiz.medwizdoctor.repository.doctor

import com.google.gson.JsonObject
import com.medwiz.medwiz.data.reponse.CommonResponse
import com.medwiz.medwiz.model.Doctors
import com.medwiz.medwizdoctor.model.LoginResponse
import retrofit2.Response

interface DoctorRepoInterface {



    suspend fun getDoctorByUserID(token:String,userId:String):Response<Doctors>
    suspend fun registerDoctor(jsonObject: JsonObject):Response<CommonResponse>
}