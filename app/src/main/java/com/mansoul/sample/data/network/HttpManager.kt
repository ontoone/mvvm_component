package com.mansoul.sample.data.network

import com.mansoul.mvvm.base.BaseApp
import com.mansoul.mvvm.http.HttpClient
import com.mansoul.mvvm.http.buildHttpClient

/**
 * @author Mansoul
 * @create 2019/3/19 22:54
 * @des
 */
class HttpManager private constructor() {

    private var httpClient: HttpClient? = null

    companion object {
        fun getInstance() = Holder.instance
    }

    private object Holder {
        val instance = HttpManager()
    }

    fun getHttpClient(): HttpClient {
        if (httpClient == null) {
            httpClient = buildHttpClient {
                context = BaseApp.instance
                baseUrl = "http://gank.io/api/"
                logEnabled = true
            }
            httpClient!!.init()
        }
        return httpClient!!
    }

    /**
     * 创建ApiService
     */
    fun <T> create(service: Class<T>): T {
        return getHttpClient().create(service)
    }

}