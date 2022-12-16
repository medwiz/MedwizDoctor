package com.medwiz.medwizdoctor.data

import com.google.gson.JsonObject
import com.medwiz.medwiz.data.reponse.CommonResponse
import com.medwiz.medwizdoctor.model.LoginResponse
import com.medwiz.medwizdoctor.util.UtilConstants
import retrofit2.Response
import retrofit2.http.*

/**
 * @Author: Prithwiraj Nath
 * @Date:16/12/22
 */
interface AuthApi {

    @POST(UtilConstants.login)
    suspend fun login(@Body jsonObject: JsonObject): Response<LoginResponse>
    @POST(UtilConstants.register)
    suspend fun register(@Body jsonObject: JsonObject):Response<CommonResponse>
    @GET(UtilConstants.getUserById+"/"+"{userId}")
    suspend fun getUserById(@Header("Authorization") accessToken: String,
                            @Path("userId")userId:String):Response<LoginResponse>

}