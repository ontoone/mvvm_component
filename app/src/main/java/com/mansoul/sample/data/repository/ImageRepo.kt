package com.mansoul.sample.data.repository

import com.mansoul.sample.data.db.ImageDao
import com.mansoul.sample.data.entity.GankImage
import com.mansoul.sample.data.network.ImageNetworkSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Mansoul
 * @create 2019/3/25 15:05
 * @des
 */
class ImageRepo(
    private val imageDao: ImageDao,
    private val imageNetworkSource: ImageNetworkSource
) {

    suspend fun getImage(): List<GankImage> {
        return withContext(Dispatchers.IO) {
            fetchImageFromNetwork()
            return@withContext imageDao.getAllImage()
        }
    }

    private suspend fun fetchImageFromNetwork() {
        val image = imageNetworkSource.getImage()
        image.forEach {
            imageDao.insert(it)
        }
    }

}