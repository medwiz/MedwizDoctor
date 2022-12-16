package com.medwiz.medwizdoctor.repository.consulat

import com.google.gson.JsonObject
import com.medwiz.medwiz.model.Consultation
import com.medwiz.medwizdoctor.data.ConsultationApi
import com.medwiz.medwizdoctor.repository.consulat.ConsultationRepoInterface
import retrofit2.Response
import java.util.ArrayList
import javax.inject.Inject

class ConsultationRepository @Inject constructor(private val api: ConsultationApi):
    ConsultationRepoInterface {

    override suspend fun createNewConsultation(token: String,jsonObject: JsonObject): Response<Consultation> {
        return api.createNewConsultation(token,jsonObject)
    }

    override suspend fun getConsultationByDocId(token: String, id: Long,status:String): Response<ArrayList<Consultation>> {
        return api.getConsultationByDocId(token,id,status)
    }

    override suspend fun getConsultationByPatientId(
        token: String, id: Long, status: String): Response<ArrayList<Consultation>> {
        return api.getConsultationByPatientId(token,id,status)
    }

    override suspend fun updateConsultation(token: String, jsonObject: JsonObject, id: Long): Response<Consultation> {
        return api.updateConsultation(token,jsonObject,id)
    }


}