package com.medwiz.medwizdoctor.repository.file

import com.medwiz.medwizdoctor.data.FileApi
import com.medwiz.medwizdoctor.model.FileResponse
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class FileRepository @Inject constructor(private val api: FileApi): FileRepoInterface {


    override suspend fun uploadFile(token:String,file: MultipartBody.Part,doctorId:String): Response<String> {

        return api.upload(token,file,doctorId)
    }


}