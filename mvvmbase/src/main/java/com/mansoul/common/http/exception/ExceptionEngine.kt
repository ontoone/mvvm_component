package com.mansoul.common.http.exception

import android.net.ParseException
import android.nfc.FormatException
import com.google.gson.JsonParseException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import java.io.UnsupportedEncodingException
import java.net.*
import java.security.cert.CertPathValidatorException
import javax.net.ssl.SSLHandshakeException

/**
 * @author Mansoul
 * @create 2019/3/20 18:48
 * @des
 */
object ExceptionEngine {

    fun handleException(e: Throwable): HttpException {
        return when (e) {
            is HttpException -> e
            is retrofit2.HttpException -> {
                val httpException = HttpException(e!!)
                httpException.desc = HttpCode.get(e.code())
                httpException
            }
            else -> {
                val code = getExceptionCode(e)
                val httpException = HttpException(e)
                httpException.code = code
                httpException.desc = ExceptionCode[code]
//                httpException.message = e.message
                httpException
            }
        }
    }

    private fun getExceptionCode(e: Throwable): Int {
        val code: Int
        if (e is NetErrorException || e is ConnectException || e is SocketException) {
            code = ExceptionCode.NETWORD_ERROR
        } else if (e is ConnectTimeoutException) {
            code = ExceptionCode.CONNECT_TIMEOUT
        } else if (e is SocketTimeoutException) {
            code = ExceptionCode.SOCKET_TIMEOUT
        } else if (e is UnknownHostException) {
            code = ExceptionCode.UNKNOWN_HOST
        } else if (e is UnsupportedEncodingException) {
            code = ExceptionCode.UNSUPPORTED_ENCODING
        } else if (e is MalformedURLException) {
            code = ExceptionCode.MALFORMED_URL
        } else if (e is SSLHandshakeException) {
            code = ExceptionCode.SSL_ERROR
        } else if (e is CertPathValidatorException) {
            code = ExceptionCode.SSL_NOT_FOUND
        } else if (e is ClassCastException) {
            code = ExceptionCode.CLASS_CAST
        } else if (e is JsonParseException || e is JSONException) {
            code = ExceptionCode.PARSE_ERROR
        } else if (e is FormatException) {
            code = ExceptionCode.FORMAT_ERROR
        } else if (e is NullPointerException) {
            code = ExceptionCode.DATA_NULL
        } else if (e is ParseException) {
            code = ExceptionCode.DATE_PARSE
        } else {
            code = ExceptionCode.UNKNOWN
        }
        return code
    }

}
