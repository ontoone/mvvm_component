package com.mansoul.mvvm.ui.image

import androidx.lifecycle.MutableLiveData
import com.mansoul.common.base.BaseVM
import com.mansoul.mvvm.data.entity.Image
import com.mansoul.mvvm.data.network.Api
import com.mansoul.mvvm.data.network.HttpManager

class ImageViewModel : BaseVM() {
    val mImage: MutableLiveData<Image> = MutableLiveData()

    fun getImage() {
        uiLaunch {
            val image = mainApi().getImages().await()
            mImage.postValue(image)
        }
    }

    fun mainApi(): Api {
        return HttpManager.getInstance().create(Api::class.java)
    }
}
