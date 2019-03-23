package com.mansoul.mvvm.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.mansoul.mvvm.data.db.ImageDao
import com.mansoul.mvvm.data.entity.GankImage
import com.mansoul.mvvm.data.network.Api
import com.mansoul.mvvm.data.network.HttpManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepo(private var imageDao: ImageDao) {

    @WorkerThread
    fun insert(gifts: GankImage) {
        imageDao.insert(gifts)
    }

    @WorkerThread
    suspend fun delete() {
        imageDao.deleteAll()
    }


    private val _allGift = MutableLiveData<List<GankImage>>()

    suspend fun getImages(): List<GankImage> {
        return withContext(Dispatchers.IO) {
            if (imageDao.getAllImage().isEmpty()) {
                val netImage = HttpManager.getInstance().create(Api::class.java).getImages().await()
                netImage.results.forEach {
                    insert(it)
                }
            }
            return@withContext imageDao.getAllImage()
        }
    }
}