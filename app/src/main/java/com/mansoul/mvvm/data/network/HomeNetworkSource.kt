package com.mansoul.mvvm.data.network

import com.mansoul.mvvm.data.entity.Android

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