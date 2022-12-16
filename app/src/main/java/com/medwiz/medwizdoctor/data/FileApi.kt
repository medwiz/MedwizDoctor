package com.medwiz.medwizdoctor.data
import com.medwiz.medwizdoctor.model.FileResponse
import com.medwiz.medwizdoctor.util.UtilConstants
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface FileApi {
    @Multipart
    @POST(UtilConstants.uploadFileApi)
    suspend fun upload(@Part file: MultipartBody.Part): Response<FileResponse>


}