package com.mansoul.sample.data.repository

import com.mansoul.mvvm.base.BaseApp
import com.mansoul.mvvm.http.util.NetworkUtil
import com.mansoul.sample.data.db.AndroidDao
import com.mansoul.sample.data.entity.Android
import com.mansoul.sample.data.network.HomeNetworkSource
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