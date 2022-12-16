package com.medwiz.medwizdoctor.repository.prescription

import com.google.gson.JsonObject
import com.medwiz.medwizdoctor.data.PrescriptionApi
import com.medwiz.medwiz.model.Prescription
import retrofit2.Response
import javax.inject.Inject

class PrescriptionRepository @Inject constructor(private val api: PrescriptionApi):
    PrescriptionRepoInterface {
    override suspend fun create(token: String, jsonObject: JsonObject): Response<Prescription> {
        return api.create(token,jsonObject)
    }

    override suspend fun getPrescriptionList(token: String, id: Long): Response<ArrayList<Prescription>> {
        return api.getPrescriptionList(token,id.toString())
    }


}