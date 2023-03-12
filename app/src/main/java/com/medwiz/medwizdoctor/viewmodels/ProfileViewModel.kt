package com.medwiz.medwizdoctor.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medwiz.medwizdoctor.repository.profile.MyProfileRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

/**
 * @Author: Prithwiraj Nath
 * @Date:06/03/23
 */
class ProfileViewModel @Inject constructor(
    private val myProfileRepository: MyProfileRepository
) : ViewModel() {

//
//    val uploadFile = SingleLiveData<FileUploadRequest>()
//    val imageUpload = uploadFile.switchMap { uploadRequest ->
//        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
//            emit(Event(Resource.loading(null)))
//            emit(uploadRequest.file?.let { imageFile ->
//                val requestFile: RequestBody =
//                    RequestBody.create(MediaType.parse("multipart/form-data"), imageFile)
//                val body: MultipartBody.Part =
//                    MultipartBody.Part.createFormData("file", imageFile.name, requestFile)
//                Event(BaseDataSource.getResult {
//                    myProfileRepository.uploadFile(body)
//                })
//            })
//        }
//    }
}