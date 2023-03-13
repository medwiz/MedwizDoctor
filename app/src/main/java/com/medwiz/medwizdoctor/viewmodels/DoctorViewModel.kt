package com.medwiz.medwizdoctor.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.medwiz.medwiz.data.reponse.CommonResponse
import com.medwiz.medwiz.model.Doctors
import com.medwiz.medwizdoctor.repository.doctor.DoctorRepoInterface
import com.medwiz.medwizdoctor.model.LoginResponse
import com.medwiz.medwizdoctor.util.NetworkUtils
import com.medwiz.medwizdoctor.util.Resource
import com.medwiz.medwizdoctor.util.UtilConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(private val repository: DoctorRepoInterface, @ApplicationContext private val context: Context):ViewModel() {

    val getDoctor: MutableLiveData<Resource<Doctors>> = MutableLiveData()
    var getDoctorResponse: Doctors? = null

    val registerDoctor: MutableLiveData<Resource<CommonResponse>> = MutableLiveData()
    var registerDoctorResponse: CommonResponse? = null




    public fun getDoctorByEmail(token:String,userId:String)=viewModelScope.launch {
        callGetUserApi(token,userId)
    }
    private suspend fun callGetUserApi(token:String,userId:String){
        getDoctor.postValue(Resource.Loading())
        try{
            if(NetworkUtils.isInternetAvailable(context)){
                val response = repository.getDoctorByUserID(token,userId)
                getDoctor.postValue(handleGetUserResponse(response))
            }
            else
                getDoctor.postValue(Resource.Error("No Internet Connection"))
        }
        catch (ex: Exception){
            when(ex){
                is IOException -> getDoctor.postValue(Resource.Error("Network Failure"))
                else -> getDoctor.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleGetUserResponse(response: Response<Doctors>): Resource<Doctors> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(resultResponse.user.id>0) {
                    getDoctorResponse = resultResponse
                    return Resource.Success(getDoctorResponse ?: resultResponse)
                }
            }
        }else if(response.code()==500||response.code()==401){
            return Resource.Error(UtilConstants.unauthorized)
        }
        else{
            val commonResponse = Gson().fromJson( response.errorBody()!!.string(), CommonResponse::class.java)
            return Resource.Error(commonResponse.message)
        }
        return Resource.Error(response.message())
    }

    public fun updateDoctor(jsonObj: JsonObject)=viewModelScope.launch {
        callRegisterDoctorApi(jsonObj)
    }
    private suspend fun callRegisterDoctorApi(jsonObj: JsonObject){
        registerDoctor.postValue(Resource.Loading())
        try{
            if(NetworkUtils.isInternetAvailable(context)){
                val response = repository.registerDoctor(jsonObj)
                registerDoctor.postValue(handleRegisterDoctorResponse(response))
            }
            else
                registerDoctor.postValue(Resource.Error("No Internet Connection"))
        }
        catch (ex: Exception){
            when(ex){
                is IOException -> registerDoctor.postValue(Resource.Error("Network Failure"))
                else -> registerDoctor.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleRegisterDoctorResponse(response: Response<CommonResponse>): Resource<CommonResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                // if(resultResponse.id>0) {
                registerDoctorResponse = resultResponse
                return Resource.Success(registerDoctorResponse ?: resultResponse)
                // }
            }
        }
        else{
            val commonResponse = Gson().fromJson( response.errorBody()!!.string(), CommonResponse::class.java)
            return Resource.Error(commonResponse.message)
        }
        return Resource.Error(response.message())
    }

}