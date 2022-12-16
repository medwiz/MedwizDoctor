package com.medwiz.medwizdoctor.repository.auth

import com.google.gson.JsonObject
import com.medwiz.medwiz.data.reponse.CommonResponse
import com.medwiz.medwizdoctor.data.AuthApi
import com.medwiz.medwizdoctor.model.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val api: AuthApi): AuthRepoInterface {

    override suspend fun login( jsonObject: JsonObject): Response<LoginResponse> {
        return api.login(jsonObject)
    }


    override suspend fun register(jsonObject: JsonObject): Response<CommonResponse> {
        return api.register(jsonObject)
    }

    override suspend fun getUserById(token:String,id: String): Response<LoginResponse> {
        return api.getUserById(token,id)
    }



}