package com.mansoul.sample.data.network

import com.mansoul.sample.data.entity.GankImage

/**
 * @author Mansoul
 * @create 2019/3/25 15:42
 * @des
 */
class ImageNetworkSource(private val api: Api) {

    suspend fun getImage(): List<GankImage> {
        val image = api.getImages().await()
        return image.results
    }

}