package com.medwiz.medwizdoctor.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author: Prithwiraj Nath
 * @Date:30/01/23
 */
@HiltViewModel
class BottomSheetViewModel @Inject constructor(): ViewModel()
{
    var phoneNumber = MutableLiveData<String>()
    var pin = MutableLiveData<String>()
    var newPassword = MutableLiveData<String>()


}