package com.mansoul.mvvm.http

import android.content.Context
import android.text.TextUtils
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mansoul.mvvm.http.interceptor.HeadersInterceptor
import com.mansoul.mvvm.http.util.HttpsUtils
import okhttp3.CertificatePinner
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

const val DEFAULT_TIMEOUT = 8L
const val DEFAULT_MAX_IDLE_CONNECTIONS = 5
const val DEFAULT_KEEP_ALIVEDURATION: Long = 8

/**
 * @author Mansoul
 * @create 2019/3/19 18:13
 * @des 网络client
 */
data class HttpClient constructor(
    var context: Context? = null,
    var baseUrl: String = "",
    var connectTimeout: Timeout = Timeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS),
    var readTimeout: Timeout = Timeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS),
    var writeTimeout: Timeout = Timeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS),
    var converterFactories: List<Converter.Factory>? = null,
    var callAdapterFactories: List<CallAdapter.Factory>? = null,
    var callFactory: okhttp3.Call.Factory? = null,
    var validateEagerly: Boolean = true,

    var loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor(),
    var headersInterceptor: Interceptor? = null,
    var interceptors: ArrayList<Interceptor>? = null,
    var networkInterceptors: ArrayList<Interceptor>? = null,

    var connectionPool: ConnectionPool? = null,
    var sslParams: HttpsUtils.SSLParams? = null,
    var hostnameVerifier: HostnameVerifier? = null,
    var certificatePinner: CertificatePinner? = null,
    var logEnabled: Boolean = false
) {
    lateinit var retrofit: Retrofit
    lateinit var okHttpClient: OkHttpClient

    fun init() {
        if (context == null) {
            throw NullPointerException("HttpClient context can not be null")
        }
        if (TextUtils.isEmpty(baseUrl)) {
            throw IllegalStateException("Base URL required.")
        }
        // 初始化Retrofit.Builder
        val retrofitBuilder = generateRetrofit()
        // 初始化OkHttpClient.Builder
        val okhttpBuilder = generateOkHttpClient()

        // 创建OkHttpClient
        okHttpClient = okhttpBuilder.build()
        // 设置Retrofit client
        retrofitBuilder.client(okHttpClient)
        // 创建Retrofit
        retrofit = retrofitBuilder.build()
    }

    private fun generateRetrofit(): Retrofit.Builder {
        val retrofitBuilder = Retrofit.Builder()
        // 设置一个API基础URL
        retrofitBuilder.baseUrl(baseUrl)

        // 添加用于序列化和反序列化对象的转化库
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create())
        if (converterFactories != null) {
            for (factory in converterFactories!!) {
                retrofitBuilder.addConverterFactory(factory)
            }
        }

        // 添加回调库
        retrofitBuilder.addCallAdapterFactory(CoroutineCallAdapterFactory())
        if (callAdapterFactories != null) {
            for (factory in callAdapterFactories!!) {
                retrofitBuilder.addCallAdapterFactory(factory)
            }
        }

        // 指定用于创建Call实例的自定义调用工厂
        if (callFactory != null) {
            retrofitBuilder.callFactory(callFactory!!)
        }

        // 设置是否在调用{@link Retrofit#create(Class)}方法时检测接口定义是否正确
        retrofitBuilder.validateEagerly(validateEagerly)
        return retrofitBuilder
    }

    private fun generateOkHttpClient(): OkHttpClient.Builder {
        val okhttpBuilder = OkHttpClient.Builder() // 创建OkHttpClient.Builder对象

        // 设置连接超时时间
        okhttpBuilder.connectTimeout(connectTimeout.timeout, connectTimeout.unit)
        // 设置读取超时时间
        okhttpBuilder.readTimeout(readTimeout.timeout, readTimeout.unit)
        // 设置写入超时时间
        okhttpBuilder.writeTimeout(writeTimeout.timeout, writeTimeout.unit)

        // 设置请求头
        if (headersInterceptor != null) {
            okhttpBuilder.addInterceptor(headersInterceptor!!)
        }

        // 添加拦截器
        if (interceptors != null) {
            okhttpBuilder.interceptors().addAll(interceptors!!)
        }
        // 添加网络拦截器
        if (networkInterceptors != null) {
            okhttpBuilder.networkInterceptors().addAll(networkInterceptors!!)
        }
        // 设置用于复用HTTP和HTTPS连接的连接池
        if (connectionPool == null) {
            connectionPool = ConnectionPool(DEFAULT_MAX_IDLE_CONNECTIONS, DEFAULT_KEEP_ALIVEDURATION, TimeUnit.SECONDS)
        }
        okhttpBuilder.connectionPool(connectionPool!!)
        // 设置HTTPS安全套接字工厂
        if (sslParams != null && sslParams!!.sSLSocketFactory != null && sslParams!!.trustManager != null) {
            okhttpBuilder.sslSocketFactory(sslParams!!.sSLSocketFactory!!, sslParams!!.trustManager!!)
        }
        // 设置主机名验证
        if (hostnameVerifier != null) {
            okhttpBuilder.hostnameVerifier(hostnameVerifier!!)
        }
        // 添加网络请求日志拦截器
        if (logEnabled) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        okhttpBuilder.addInterceptor(loggingInterceptor)

//        // 设置网络请求缓存
//        cacheEnabled = builder.cacheEnabled
//        cache = builder.cache
//        if (cacheEnabled && cache != null) {
//            okhttpBuilder.cache(cache)
//        }
//        // 设置Cookie管理器
//        cookieEnabled = builder.cookieEnabled
//        cookieJar = builder.cookieJar
//        if (cookieEnabled && cookieJar == null) {
//            okhttpBuilder.cookieJar(OkHttpCookieManager(OkHttpCookieCache(), OkHttpCookiePersistor(context)))
//        }
//        if (cookieJar != null) {
//            okhttpBuilder.cookieJar(cookieJar)
//        }
//        // 设置当异步请求执行策略
//        dispatcher = builder.dispatcher
//        if (dispatcher != null) {
//            okhttpBuilder.dispatcher(dispatcher)
//        }
//        // 设置身份验证
//        authenticator = builder.authenticator
//        if (authenticator != null) {
//            okhttpBuilder.authenticator(authenticator)
//        }
//        // 设置限制被信任的认证中心
//        certificatePinner = builder.certificatePinner
//        if (certificatePinner != null) {
//            okhttpBuilder.certificatePinner(certificatePinner)
//        }
//        // 设置代理
//        proxy = builder.proxy
//        if (proxy != null) {
//            okhttpBuilder.proxy(proxy)
//        }
//        // 设置是否支持HTTP重定向
//        followRedirects = builder.followRedirects
//        okhttpBuilder.followRedirects(followRedirects)
//        // 设置是否支持HTTPS重定向
//        followSslRedirects = builder.followSslRedirects
//        okhttpBuilder.followSslRedirects(followSslRedirects)
//        // 设置是否允许连接失败时重试
//        retryOnConnectionFailure = builder.retryOnConnectionFailure
//        okhttpBuilder.retryOnConnectionFailure(retryOnConnectionFailure)
        return okhttpBuilder
    }

    /**
     * 创建ApiService
     */
    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}

fun buildHttpClient(buildAction: HttpClient.() -> Unit) = HttpClient().apply(buildAction)

/**
 * @author Mansoul
 * @create 2019/3/19 18:23
 * @des 超时时间信息(包含时间和单位)
 */
data class Timeout constructor(
    val timeout: Long,
    val unit: TimeUnit
)

