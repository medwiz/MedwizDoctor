package com.medwiz.medwizdoctor.data
import com.medwiz.medwiz.data.reponse.CommonResponse
import com.medwiz.medwizdoctor.model.FileResponse
import com.medwiz.medwizdoctor.util.UtilConstants
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface FileApi {
    @Multipart
    @POST(UtilConstants.uploadProfilePictureApi+"{doctorUserId}")
    suspend fun upload(@Header("Authorization") accessToken: String,
                       @Part file: MultipartBody.Part,
                       @Path("doctorUserId")doctorUserId:String): Response<CommonResponse>


}