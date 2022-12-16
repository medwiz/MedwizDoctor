package com.medwiz.medwizdoctor.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.medwiz.medwiz.data.reponse.CommonResponse
import com.medwiz.medwiz.model.Consultation
import com.medwiz.medwizdoctor.repository.consulat.ConsultationRepoInterface
import com.medwiz.medwizdoctor.util.NetworkUtils
import com.medwiz.medwizdoctor.util.Resource
import com.medwiz.medwizdoctor.util.UtilConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class ConsultationViewModel @Inject constructor(private val repository: ConsultationRepoInterface, @ApplicationContext private val context: Context):ViewModel() {


    val consultation:MutableLiveData<Resource<Consultation>> = MutableLiveData()
    var consultationResponse:Consultation?=null


    val consultationList:MutableLiveData<Resource<ArrayList<Consultation>>> = MutableLiveData()
    var consultationResponseList:ArrayList<Consultation>?=null


    public fun createNewConsultation(token:String,jsonObject: JsonObject)=viewModelScope.launch {

        callConsultationApi(token,jsonObject)

    }
    private suspend fun callConsultationApi(token:String,jsonObject: JsonObject){
        consultation.postValue(Resource.Loading())
        try{
            if(NetworkUtils.isInternetAvailable(context)){
                val response = repository.createNewConsultation(token,jsonObject)
                consultation.postValue(handleConsultationResponse(response))
            }
            else
                consultation.postValue(Resource.Error("No Internet Connection"))
        }
        catch (ex: Exception){
            when(ex){
                is IOException -> consultation.postValue(Resource.Error("Network Failure"))
                else -> consultation.postValue(Resource.Error("Conversion Error"))
            }
        }
    }
    private fun handleConsultationResponse(response: Response<Consultation>): Resource<Consultation> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(resultResponse.docId>0) {
                    consultationResponse = resultResponse
                    return Resource.Success(consultationResponse ?: resultResponse)
                }else{
                    return Resource.Error("")
                }
            }
        }
        else{
            val commonResponse = Gson().fromJson( response.errorBody()!!.string(), CommonResponse::class.java)
            return Resource.Error(commonResponse.message)
        }
        return Resource.Error(response.message())
    }


    public fun getConsultationByDocId(token:String,id:Long,status:String)=viewModelScope.launch {

        callGetConsultationApi(token,id,status)

    }
    private suspend fun callGetConsultationApi(token:String,id:Long,status:String){
        consultationList.postValue(Resource.Loading())
        try{
            if(NetworkUtils.isInternetAvailable(context)){
                val response = repository.getConsultationByDocId(token,id,status)
                consultationList.postValue(handleConsultationByDocResponse(response))
            }
            else
                consultationList.postValue(Resource.Error("No Internet Connection"))
        }
        catch (ex: Exception){
            when(ex){
                is IOException -> consultationList.postValue(Resource.Error("Network Failure"))
                else -> consultationList.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleConsultationByDocResponse(response: Response<ArrayList<Consultation>>): Resource<ArrayList<Consultation>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(resultResponse.isNotEmpty()) {
                    consultationResponseList = resultResponse
                    return Resource.Success(consultationResponseList ?: resultResponse)
                }
            }
        }
        else if(response.code()==401){
            return Resource.Error(UtilConstants.unauthorized)
        }
        else{
            val commonResponse = Gson().fromJson( response.errorBody()!!.string(), CommonResponse::class.java)
            return Resource.Error(commonResponse.message)
        }
        return Resource.Error(response.message())
    }

    fun update(token:String,jsonObject: JsonObject,id:Long)=viewModelScope.launch{
        callConsultationUpdateApi(token,jsonObject,id)
    }

    private suspend fun callConsultationUpdateApi(token:String,jsonObject: JsonObject,id:Long){
        consultation.postValue(Resource.Loading())
        try{
            if(NetworkUtils.isInternetAvailable(context)){
                val response = repository.updateConsultation(token,jsonObject,id)
                consultation.postValue(handleConsultationResponse(response))
            }
            else
                consultation.postValue(Resource.Error("No Internet Connection"))
        }
        catch (ex: Exception){
            when(ex){
                is IOException -> consultation.postValue(Resource.Error("Network Failure"))
                else -> consultation.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    fun getConsultationByPatientId(token: String, userId: Long, status: String) =viewModelScope.launch {

        callGetPatientConsultationApi(token,userId,status)

    }

    private suspend fun callGetPatientConsultationApi(token:String,id:Long,status: String){
        consultationList.postValue(Resource.Loading())
        try{
            if(NetworkUtils.isInternetAvailable(context)){
                val response = repository.getConsultationByPatientId(token,id,status)
                consultationList.postValue(handleConsultationListResponse(response))
            }
            else
                consultationList.postValue(Resource.Error("No Internet Connection"))
        }
        catch (ex: Exception){
            when(ex){
                is IOException -> consultationList.postValue(Resource.Error("Network Failure"))
                else -> consultationList.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleConsultationListResponse(response: Response<ArrayList<Consultation>>): Resource<ArrayList<Consultation>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(resultResponse.isNotEmpty()) {
                    consultationResponseList = resultResponse
                    return Resource.Success(consultationResponseList ?: resultResponse)
                }else{
                    consultationResponseList = resultResponse
                    return Resource.Success(consultationResponseList ?: resultResponse)
                }
            }
        }
        else if(response.code()==401){
            return Resource.Error(UtilConstants.unauthorized)
        }
        else{
            val commonResponse = Gson().fromJson( response.errorBody()!!.string(), CommonResponse::class.java)
            return Resource.Error(commonResponse.message)
        }
        return Resource.Error(response.message())
    }


}