package com.medwiz.medwizdoctor.repository.file

import com.medwiz.medwiz.data.reponse.CommonResponse
import com.medwiz.medwizdoctor.model.FileResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface FileRepoInterface {

    suspend fun uploadFile(token:String,file: MultipartBody.Part,doctorId:String):Response<CommonResponse>

}