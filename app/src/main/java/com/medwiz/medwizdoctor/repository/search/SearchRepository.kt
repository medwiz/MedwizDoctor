package com.medwiz.medwizdoctor.repository.search

import com.medwiz.medwizdoctor.data.SearchApi
import com.medwiz.medwizdoctor.model.MedicineResponse
import retrofit2.Response
import java.util.ArrayList
import javax.inject.Inject

/**
 * @Author: Prithwiraj Nath
 * @Date:11/12/22
 */
class SearchRepository @Inject constructor(private val api: SearchApi): SearchRepoInterface {

    override suspend fun searchMedicine(type:String,keyword: String,isMedicine:Boolean): Response<ArrayList<MedicineResponse>> {
        if(isMedicine){
        return api.searchMedicine(type,keyword)
        }else{
            return api.searchLabTest(keyword)
        }
    }
}