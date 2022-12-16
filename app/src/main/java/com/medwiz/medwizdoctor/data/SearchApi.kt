package com.medwiz.medwizdoctor.data
import com.medwiz.medwizdoctor.model.MedicineResponse
import retrofit2.Response
import retrofit2.http.*
import java.util.ArrayList

interface SearchApi {

    @GET("medicine/search/{type}/{keyword}")
    suspend fun searchMedicine(@Path("type")type:String,
                               @Path("keyword")keyword:String): Response<ArrayList<MedicineResponse>>

    @GET("labtest/search/{keyword}")
    suspend fun searchLabTest(@Path("keyword")keyword:String): Response<ArrayList<MedicineResponse>>


}