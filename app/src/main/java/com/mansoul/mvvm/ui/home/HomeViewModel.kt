package com.mansoul.mvvm.ui.home

import androidx.lifecycle.MutableLiveData
import com.mansoul.common.base.BaseApp
import com.mansoul.common.base.BaseVM
import com.mansoul.mvvm.data.db.MyDatabase
import com.mansoul.mvvm.data.entity.GankImage
import com.mansoul.mvvm.data.repository.HomeRepo
import com.orhanobut.logger.Logger

class HomeViewModel : BaseVM() {

    val homeRepo: HomeRepo

    val mImage: MutableLiveData<List<GankImage>> = MutableLiveData()


    init {
        val imageDao = MyDatabase.getDatabase(BaseApp.applicationContext()).imageDao()
        homeRepo = HomeRepo(imageDao)
    }

    fun getImage() {
        uiLaunch {
            val images = homeRepo.getImages()
            Logger.e(images.toString())
            mImage.postValue(images)
        }
    }

    fun delete() {
        ioLaunch {
            homeRepo.delete()
        }
    }


}
