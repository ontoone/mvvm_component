package com.mansoul.mvvm

import androidx.lifecycle.MutableLiveData
import com.mansoul.common.base.BaseVM
import com.mansoul.mvvm.data.network.Api
import com.mansoul.mvvm.data.network.HttpManager
import okhttp3.ResponseBody

/**
 * @author Mansoul
 * @create 2019/3/20 15:00
 * @des
 */
class MainVM : BaseVM() {

    val mWeather: MutableLiveData<ResponseBody> = MutableLiveData()

    fun getWeather() {
        vmLaunch {
            val responseBody = mainApi().getCurrentWeather().await()
            mWeather.postValue(responseBody)
        }
    }

    fun mainApi(): Api {
        return HttpManager.getInstance().create(Api::class.java)
    }
}