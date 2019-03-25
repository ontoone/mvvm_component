package com.mansoul.mvvm.ui.image

import androidx.lifecycle.MutableLiveData
import com.mansoul.common.base.BaseVM
import com.mansoul.mvvm.data.entity.GankImage
import com.mansoul.mvvm.data.repository.ImageRepo

class ImageViewModel(private val imageRepo: ImageRepo) : BaseVM() {
    val image: MutableLiveData<List<GankImage>> = MutableLiveData()

    suspend fun getImage() {
        val image = imageRepo.getImage()
        this.image.postValue(image)
    }
}
