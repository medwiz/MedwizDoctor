package com.medwiz.medwizdoctor.repository.profile

import com.medwiz.medwizdoctor.data.MyProfileApi
import com.medwiz.medwizdoctor.model.FileResponse
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

/**
 * @Author: Prithwiraj Nath
 * @Date:06/03/23
 */
class MyProfileRepository @Inject constructor(
    private val myProfileApi: MyProfileApi
) : MyProfileInterface {

    override suspend fun uploadFile(imageFile: MultipartBody.Part): Response<FileResponse> {
        TODO("Not yet implemented")
    }


}