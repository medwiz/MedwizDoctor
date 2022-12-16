package com.medwiz.medwizdoctor.repository.file

import com.medwiz.medwizdoctor.data.FileApi
import com.medwiz.medwizdoctor.model.FileResponse
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class FileRepository @Inject constructor(private val api: FileApi): FileRepoInterface {


    override suspend fun uploadFile(file: MultipartBody.Part): Response<FileResponse> {

        return api.upload(file)
    }


}