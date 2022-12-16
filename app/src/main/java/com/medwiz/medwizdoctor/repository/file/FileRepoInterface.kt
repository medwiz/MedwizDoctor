package com.medwiz.medwizdoctor.repository.file

import com.medwiz.medwizdoctor.model.FileResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface FileRepoInterface {

    suspend fun uploadFile(file: MultipartBody.Part):Response<FileResponse>

}