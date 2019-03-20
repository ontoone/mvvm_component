package com.mansoul.common.http.exception

import okhttp3.Request
import retrofit2.Response
import java.nio.charset.Charset
import java.util.*

/**
 * Http请求异常
 *
 * @author liyunlong
 * @date 2017/2/4 11:31
 */
class HttpException : Exception {

    var code: Int = 0
    var desc: String? = null
    var httpUrl: String? = null
    var responseTime: String? = null
    var responseContent: String? = null
    var request: Request? = null
    var response: Response<*>? = null

    /**
     * 是否为网络错误
     */
    val isNetworkError: Boolean
        get() = ExceptionCode.NETWORD_ERROR == code

    /**
     * 是否为网络不佳
     */
    val isNetworkPoor: Boolean
        get() = Arrays.asList(408, ExceptionCode.CONNECT_TIMEOUT, ExceptionCode.SOCKET_TIMEOUT).contains(code)

    constructor(throwable: Throwable) : super(throwable)

    constructor(httpException: retrofit2.HttpException?) {
        if (httpException != null) {
            this.code = httpException.code()
            this.response = httpException.response()
            if (response != null) {
                this.request = response!!.raw().request()
                if (request != null) {
                    this.httpUrl = request!!.url().toString()
                }
                var responseBody = response!!.errorBody()
                if (responseBody == null) {
                    responseBody = response!!.raw().body()
                }
                if (responseBody != null) {
                    val source = responseBody.source()
                    val buffer = source.buffer()
                    this.responseContent = buffer.clone().readString(Charset.forName("UTF-8"))
                }
            }
        }
    }

    constructor(throwable: Throwable, request: Request?) : super(throwable) {
        this.request = request
        if (throwable is HttpException) {
            this.code = throwable.code
            this.desc = throwable.desc
        }
        if (request != null) {
            this.httpUrl = request.url().toString()
        }
    }


    override fun toString(): String {
        return "HttpException{" +
                "code=" + code +
                ", desc='" + desc + '\''.toString() +
                ", message='" + message + '\''.toString() +
                ", httpUrl='" + httpUrl + '\''.toString() +
                ", responseTime='" + responseTime + '\''.toString() +
                ", responseContent='" + responseContent + '\''.toString() +
                '}'.toString()
    }

}
