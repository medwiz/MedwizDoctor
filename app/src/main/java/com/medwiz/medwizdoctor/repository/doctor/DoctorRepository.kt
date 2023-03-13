package com.medwiz.medwizdoctor.repository.doctor

import com.google.gson.JsonObject
import com.medwiz.medwizdoctor.data.DoctorApi
import com.medwiz.medwiz.data.reponse.CommonResponse
import com.medwiz.medwiz.model.Doctors
import com.medwiz.medwizdoctor.model.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class DoctorRepository @Inject constructor(private val api: DoctorApi): DoctorRepoInterface {

    override suspend fun getDoctorByUserID(token: String, userId: String): Response<Doctors> {
        return api.getDoctorByUserId(token,userId)
    }

    override suspend fun registerDoctor(jsonObject: JsonObject): Response<CommonResponse> {
        return api.registerDoctor(jsonObject)
    }


}