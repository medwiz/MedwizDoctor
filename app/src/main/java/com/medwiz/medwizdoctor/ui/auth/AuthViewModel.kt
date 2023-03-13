package com.medwiz.medwizdoctor.ui.auth

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.medwiz.medwiz.data.reponse.CommonResponse
import com.medwiz.medwizdoctor.model.RegisterRequest
import com.medwiz.medwizdoctor.repository.auth.AuthRepoInterface
import com.medwiz.medwizdoctor.R
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
class AuthViewModel @Inject constructor(private val repository: AuthRepoInterface, @ApplicationContext private val context: Context):ViewModel() {

    val login: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    var loginResponse: LoginResponse? = null

    val register: MutableLiveData<Resource<CommonResponse>> = MutableLiveData()
    var registerResponse: CommonResponse? = null

    val getUser: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    var getUserResponse: LoginResponse? = null




    //signUp

    fun signUp(request: RegisterRequest)=viewModelScope.launch {
        val requestObj = JsonObject()
        requestObj.addProperty("userPhoneNumber",request.mobile)
        requestObj.addProperty(UtilConstants.PASSWORD, request.password)
        requestObj.addProperty(UtilConstants.email, request.email)
        requestObj.addProperty("firstName",request.firstname)
        requestObj.addProperty("lastName",request.lastname)
        requestObj.addProperty("gender",request.gender)
        requestObj.addProperty("age",request.age)
        requestObj.addProperty("roleName","DOCTOR")
        requestObj.addProperty("address1",request.address)
        requestObj.addProperty("address2","")
        requestObj.addProperty("city",request.city)
        requestObj.addProperty("state",request.state)
        requestObj.addProperty("zip",request.pinCode)
        requestObj.addProperty("country","India")
        requestObj.addProperty("latitude",0.0)
        requestObj.addProperty("longitude",0.0)
        callRegisterApi(requestObj)
    }

    private suspend fun callRegisterApi(jsonObject: JsonObject){
        register.postValue(Resource.Loading())
        try{
            if(NetworkUtils.isInternetAvailable(context)){
                val response = repository.register(jsonObject)
                register.postValue(handleRegisterResponse(response))
            }
            else
                register.postValue(Resource.Error("No Internet Connection"))
        }
        catch (ex: Exception){
            when(ex){
                is IOException -> register.postValue(Resource.Error("Network Failure"))
                else -> register.postValue(Resource.Error("Conversion Error"))
            }
        }
    }
    private fun handleRegisterResponse(response: Response<CommonResponse>): Resource<CommonResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(resultResponse.success) {
                    registerResponse = resultResponse
                    return Resource.Success(resultResponse ?: resultResponse)
                }
            }
        }
        else{
            val commonResponse = Gson().fromJson( response.errorBody()!!.string(), CommonResponse::class.java)
            return Resource.Error(commonResponse.message)
        }
        return Resource.Error(response.message())
    }

    fun login(username:String,password:String)=viewModelScope.launch {
        val requestObj = JsonObject()
        if (username.isEmpty()) {
            login.postValue(Resource.Error(context.getString(R.string.invalid_username)))
            return@launch
        }

        if (password.isEmpty()) {
            login.postValue(Resource.Error(context.getString(R.string.password_error)))
            return@launch
        }
        if (username.length < 3) {
            login.postValue(Resource.Error(context.getString(R.string.invalid_username)))
            return@launch
        }
        if (password.length < 3) {
            login.postValue(Resource.Error(context.getString(R.string.paasword_length_error)))
            return@launch
        }

        requestObj.addProperty(UtilConstants.USERPHONENUMBER, username)
        requestObj.addProperty(UtilConstants.PASSWORD, password)
        callLoginApi(requestObj)
    }

    private suspend fun callLoginApi(jsonObject: JsonObject){
        login.postValue(Resource.Loading())
        try{
            if(NetworkUtils.isInternetAvailable(context)){
                val response = repository.login(jsonObject)
                login.postValue(handleLoginResponse(response))
            }
            else
                login.postValue(Resource.Error("No Internet Connection"))
        }
        catch (ex: Exception){
            when(ex){
                is IOException -> login.postValue(Resource.Error("Network Failure"))
                else -> login.postValue(Resource.Error("Conversion Error"))
            }
        }
    }
    private fun handleLoginResponse(response: Response<LoginResponse>): Resource<LoginResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(resultResponse.token.isNotEmpty()) {
                    loginResponse = resultResponse
                    return Resource.Success(loginResponse ?: resultResponse)
                }
            }
        }
        else if(response.code()==500||response.code()==401){
            return Resource.Error(UtilConstants.unauthorized)
        }
        else{
            val commonResponse = Gson().fromJson( response.errorBody()!!.string(), CommonResponse::class.java)
            return Resource.Error(commonResponse.message)
        }
        return Resource.Error(response.message())
    }

    public fun getUserById(token:String,id:String)=viewModelScope.launch {
        callGetUserApi(token,id)
    }
    private suspend fun callGetUserApi(token:String,id:String){
        getUser.postValue(Resource.Loading())
        try{
            if(NetworkUtils.isInternetAvailable(context)){
                val response = repository.getUserById(token,id)
                getUser.postValue(handleGetUserResponse(response))
            }
            else
                getUser.postValue(Resource.Error("No Internet Connection"))
        }
        catch (ex: Exception){
            when(ex){
                is IOException -> getUser.postValue(Resource.Error("Network Failure"))
                else -> getUser.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleGetUserResponse(response: Response<LoginResponse>): Resource<LoginResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(resultResponse.user.id>0) {
                    getUserResponse = resultResponse
                    return Resource.Success(getUserResponse ?: resultResponse)
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