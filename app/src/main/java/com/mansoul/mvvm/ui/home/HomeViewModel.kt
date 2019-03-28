package com.mansoul.mvvm.ui.home

import androidx.lifecycle.MutableLiveData
import com.mansoul.common.base.BaseVM
import com.mansoul.mvvm.data.entity.Android
import com.mansoul.mvvm.data.repository.HomeRepo

class HomeViewModel(val homeRepo: HomeRepo) : BaseVM() {
    val android: MutableLiveData<List<Android>> = MutableLiveData()

    suspend fun getAndroid() {
        val android = homeRepo.getAndroid()
        this.android.postValue(android)
    }

}
