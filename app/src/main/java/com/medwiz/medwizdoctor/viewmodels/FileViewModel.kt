package com.medwiz.medwizdoctor.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.medwiz.medwiz.data.reponse.CommonResponse
import com.medwiz.medwizdoctor.repository.file.FileRepoInterface
import com.medwiz.medwizdoctor.model.FileResponse
import com.medwiz.medwizdoctor.util.NetworkUtils
import com.medwiz.medwizdoctor.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FileViewModel @Inject constructor(private val repository: FileRepoInterface, @ApplicationContext private val context: Context):ViewModel() {


    val uploadFile:MutableLiveData<Resource<FileResponse>> = MutableLiveData()
    var uploadFileResponse:FileResponse?=null



    public fun uploadFile(file: MultipartBody.Part)=viewModelScope.launch {
        callFileUploadApi(file)
    }
    private suspend fun callFileUploadApi(file: MultipartBody.Part){
        uploadFile.postValue(Resource.Loading())
        try{
            if(NetworkUtils.isInternetAvailable(context)){
                val response = repository.uploadFile(file)
                uploadFile.postValue(handleFileUploadResponse(response))
            }
            else
                uploadFile.postValue(Resource.Error("No Internet Connection"))
        }
        catch (ex: Exception){
            when(ex){
                is IOException -> uploadFile.postValue(Resource.Error("Network Failure"))
                else -> uploadFile.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleFileUploadResponse(response: Response<FileResponse>): Resource<FileResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(resultResponse.fileSize>0) {
                    uploadFileResponse = resultResponse
                    return Resource.Success(uploadFileResponse ?: resultResponse)
                }
            }
        }
        else{
            val commonResponse = Gson().fromJson( response.errorBody()!!.string(), CommonResponse::class.java)
            return Resource.Error(commonResponse.message)
        }
        return Resource.Error(response.message())
    }

}