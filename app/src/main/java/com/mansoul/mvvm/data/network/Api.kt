package com.mansoul.mvvm.data.network

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.GET

/**
 * @author Mansoul
 * @create 2019/3/19 23:28
 * @des
 */
interface Api {

    /**
     * TEST
     * 获取城市信息
     */
    @GET("http://pv.sohu.com/cityjson?ie=utf-8&qq-pf-to=pcqq.c2c")
    fun getCurrentWeather(): Deferred<ResponseBody>

}