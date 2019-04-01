package com.mansoul.sample.ui.image

import androidx.lifecycle.MutableLiveData
import com.mansoul.mvvm.base.BaseVM
import com.mansoul.sample.data.entity.GankImage
import com.mansoul.sample.data.repository.ImageRepo

class ImageViewModel(private val imageRepo: ImageRepo) : BaseVM() {
    val image: MutableLiveData<List<GankImage>> = MutableLiveData()

    suspend fun getImage() {
        val image = imageRepo.getImage()
        this.image.postValue(image)
    }
}
