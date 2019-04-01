package com.mansoul.sample.data.network

import com.mansoul.sample.data.entity.Android

/**
 * @author Mansoul
 * @create 2019/3/27 15:35
 * @des
 */
class HomeNetworkSource(private val api: Api) {

    suspend fun getAndroid(): List<Android> {
        return api.gankToday().await().results.Android
    }
}