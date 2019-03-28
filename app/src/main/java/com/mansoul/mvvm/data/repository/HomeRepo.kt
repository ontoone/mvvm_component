package com.mansoul.mvvm.data.repository

import com.mansoul.common.base.BaseApp
import com.mansoul.common.http.util.NetworkUtil
import com.mansoul.mvvm.data.db.AndroidDao
import com.mansoul.mvvm.data.entity.Android
import com.mansoul.mvvm.data.network.HomeNetworkSource
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepo(
    private val androidDao: AndroidDao,
    private val homeNetworkSource: HomeNetworkSource
) {

    suspend fun getAndroid(): List<Android> {
        return withContext(Dispatchers.IO) {
            if (NetworkUtil.isNetworkAvailable(BaseApp.applicationContext())) {
                return@withContext fetchData()
            }
            Logger.e("data form dao")
            return@withContext androidDao.getAll()
        }
    }

    @Suppress("SENSELESS_COMPARISON")
    private suspend fun fetchData(): List<Android> {
        val android = homeNetworkSource.getAndroid()
        android.forEach {
            if (it.images == null) {
                it.images = listOf()
            }
            androidDao.insert(it)
        }
        return android
    }

}