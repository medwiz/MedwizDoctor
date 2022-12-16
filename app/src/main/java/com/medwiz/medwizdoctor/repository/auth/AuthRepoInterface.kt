package com.medwiz.medwizdoctor.repository.auth

import com.google.gson.JsonObject
import com.medwiz.medwiz.data.reponse.CommonResponse
import com.medwiz.medwizdoctor.model.LoginResponse

import retrofit2.Response

interface AuthRepoInterface {


    suspend fun login( jsonObject: JsonObject): Response<LoginResponse>
    suspend fun register( jsonObject: JsonObject): Response<CommonResponse>
    suspend fun getUserById(token:String,id:String):Response<LoginResponse>
}