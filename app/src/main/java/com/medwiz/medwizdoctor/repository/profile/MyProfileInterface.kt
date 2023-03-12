package com.medwiz.medwizdoctor.repository.profile

import com.medwiz.medwizdoctor.model.FileResponse
import okhttp3.MultipartBody
import retrofit2.Response

/**
 * @Author: Prithwiraj Nath
 * @Date:06/03/23
 */
interface MyProfileInterface {
    suspend fun uploadFile(imageFile: MultipartBody.Part): Response<FileResponse>
}