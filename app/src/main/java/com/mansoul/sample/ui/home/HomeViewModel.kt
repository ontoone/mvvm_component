package com.mansoul.sample.ui.home

import androidx.lifecycle.MutableLiveData
import com.mansoul.mvvm.base.BaseVM
import com.mansoul.sample.data.entity.Android
import com.mansoul.sample.data.repository.HomeRepo

class HomeViewModel(val homeRepo: HomeRepo) : BaseVM() {
    val android: MutableLiveData<List<Android>> = MutableLiveData()

    suspend fun getAndroid() {
        val android = homeRepo.getAndroid()
        this.android.postValue(android)
    }

}
