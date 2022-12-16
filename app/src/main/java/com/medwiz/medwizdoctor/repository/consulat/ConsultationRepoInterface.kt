package com.medwiz.medwizdoctor.repository.consulat
import com.google.gson.JsonObject
import com.medwiz.medwiz.model.Consultation
import retrofit2.Response
import java.util.ArrayList

interface ConsultationRepoInterface {



    suspend fun createNewConsultation(token:String,jsonObject: JsonObject):Response<Consultation>
    suspend fun getConsultationByDocId(token:String,id: Long,status:String):Response<ArrayList<Consultation>>
    suspend fun getConsultationByPatientId(token:String,id: Long,status:String):Response<ArrayList<Consultation>>
    suspend fun updateConsultation(token:String,jsonObject: JsonObject,id: Long):Response<Consultation>


}