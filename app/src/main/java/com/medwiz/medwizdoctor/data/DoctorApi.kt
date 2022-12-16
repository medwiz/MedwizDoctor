package com.medwiz.medwizdoctor.data

import com.google.gson.JsonObject
import com.medwiz.medwiz.data.reponse.CommonResponse
import com.medwiz.medwizdoctor.model.LoginResponse
import com.medwiz.medwizdoctor.util.UtilConstants
import retrofit2.Response
import retrofit2.http.*

interface DoctorApi {

    @GET(UtilConstants.getDoctorByEmail+"{email}")
    suspend fun getDoctorByEmail(@Header("Authorization") accessToken: String,
                            @Path("email")email:String):Response<LoginResponse>

    @POST(UtilConstants.doctorApi)
    suspend fun registerDoctor(@Body jsonObject: JsonObject):Response<CommonResponse>



}