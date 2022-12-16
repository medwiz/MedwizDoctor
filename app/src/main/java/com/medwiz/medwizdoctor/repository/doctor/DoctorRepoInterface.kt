package com.medwiz.medwizdoctor.repository.doctor

import com.google.gson.JsonObject
import com.medwiz.medwiz.data.reponse.CommonResponse
import com.medwiz.medwizdoctor.model.LoginResponse
import retrofit2.Response

interface DoctorRepoInterface {



    suspend fun getDoctorByEmail(token:String,email:String):Response<LoginResponse>
    suspend fun registerDoctor(jsonObject: JsonObject):Response<CommonResponse>
}