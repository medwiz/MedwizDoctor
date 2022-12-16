package com.medwiz.medwizdoctor.data

import com.google.gson.JsonObject
import com.medwiz.medwiz.model.Consultation
import com.medwiz.medwizdoctor.util.UtilConstants
import retrofit2.Response
import retrofit2.http.*
import java.util.ArrayList

interface ConsultationApi {

    @POST(UtilConstants.consultationApi)
    suspend fun createNewConsultation(@Header("Authorization") accessToken: String,
                                      @Body jsonObject: JsonObject): Response<Consultation>

    @GET(UtilConstants.consultationApi+"doctor/{doctorId}/{status}")
    suspend fun getConsultationByDocId(@Header("Authorization") accessToken: String,
                                 @Path("doctorId")email:Long,
                                 @Path("status")status:String,
                                 ):Response<ArrayList<Consultation>>

    @GET(UtilConstants.consultationApi+"user/{userId}/{status}")
    suspend fun getConsultationByPatientId(@Header("Authorization") accessToken: String,
                                       @Path("userId")email:Long,
                                       @Path("status")status:String,
    ):Response<ArrayList<Consultation>>

    @PUT(UtilConstants.consultationApi+"{id}")
    suspend fun updateConsultation(@Header("Authorization") accessToken: String,
                                   @Body jsonObject: JsonObject,
                                   @Path("id")id:Long): Response<Consultation>
}