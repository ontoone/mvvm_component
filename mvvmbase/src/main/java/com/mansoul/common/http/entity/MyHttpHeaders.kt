package com.mansoul.common.http.entity

import android.text.TextUtils
import com.orhanobut.logger.Logger
import org.json.JSONException
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Mansoul
 * @create 2019/3/19 21:32
 * @des
 */
class MyHttpHeaders {

    var headersMap: LinkedHashMap<String, String>? = null

    val isEmpty: Boolean
        get() = headersMap!!.isEmpty()

    val names: Set<String>
        get() = headersMap!!.keys

    private fun init() {
        headersMap = LinkedHashMap()
    }

    constructor() {
        init()
    }

    constructor(key: String, value: String) {
        init()
        put(key, value)
    }

    fun put(key: String?, value: String?) {
        if (key != null && value != null) {
            headersMap!![key] = value
        }
    }

    fun put(headers: Map<String, String>?) {
        if (headers != null && !headers.isEmpty()) {
            headersMap!!.putAll(headers)
        }
    }

    fun put(headers: MyHttpHeaders?) {
        if (headers != null) {
            if (headers.headersMap != null && !headers.headersMap!!.isEmpty())
                headersMap!!.putAll(headers.headersMap!!)
        }
    }

    operator fun get(key: String): String? {
        return headersMap!![key]
    }

    fun remove(key: String): String? {
        return headersMap!!.remove(key)
    }

    fun clear() {
        headersMap!!.clear()
    }

    fun toJSONString(): String {
        val jsonObject = JSONObject()
        try {
            for ((key, value) in headersMap!!) {
                jsonObject.put(key, value)
            }
        } catch (e: JSONException) {
            Logger.e(this.javaClass.simpleName, e)
        }

        return jsonObject.toString()
    }

    override fun toString(): String {
        return "MyHttpHeaders{headersMap=$headersMap}"
    }

    companion object {

        val FORMAT_HTTP_DATA = "EEE, dd MMM y HH:mm:ss 'GMT'"
        val GMT_TIME_ZONE = TimeZone.getTimeZone("GMT")

        val HEAD_KEY_RESPONSE_CODE = "ResponseCode"
        val HEAD_KEY_RESPONSE_MESSAGE = "ResponseMessage"
        val HEAD_KEY_ACCEPT = "Accept"
        val HEAD_KEY_ACCEPT_ENCODING = "Accept-Encoding"
        val HEAD_VALUE_ACCEPT_ENCODING = "gzip, deflate"
        val HEAD_KEY_ACCEPT_LANGUAGE = "Accept-Language"
        val HEAD_KEY_CONTENT_TYPE = "Content-Type"
        val HEAD_KEY_CONTENT_LENGTH = "Content-Length"
        val HEAD_KEY_CONTENT_ENCODING = "Content-Encoding"
        val HEAD_KEY_CONTENT_DISPOSITION = "Content-Disposition"
        val HEAD_KEY_CONTENT_RANGE = "Content-Range"
        val HEAD_KEY_CACHE_CONTROL = "Cache-Control"
        val HEAD_KEY_CONNECTION = "Connection"
        val HEAD_VALUE_CONNECTION_KEEP_ALIVE = "keep-alive"
        val HEAD_VALUE_CONNECTION_CLOSE = "close"
        val HEAD_KEY_DATE = "Date"
        val HEAD_KEY_EXPIRES = "Expires"
        val HEAD_KEY_E_TAG = "ETag"
        val HEAD_KEY_PRAGMA = "Pragma"
        val HEAD_KEY_IF_MODIFIED_SINCE = "If-Modified-Since"
        val HEAD_KEY_IF_NONE_MATCH = "If-None-Match"
        val HEAD_KEY_LAST_MODIFIED = "Last-Modified"
        val HEAD_KEY_LOCATION = "Location"
        val HEAD_KEY_USER_AGENT = "User-Agent"
        val HEAD_KEY_COOKIE = "Cookie"
        val HEAD_KEY_COOKIE2 = "Cookie2"
        val HEAD_KEY_SET_COOKIE = "Set-Cookie"
        val HEAD_KEY_SET_COOKIE2 = "Set-Cookie2"

        fun getDate(gmtTime: String): Long {
            try {
                return parseGMTToMillis(gmtTime)
            } catch (e: ParseException) {
                return 0
            }

        }

        fun getDate(milliseconds: Long): String {
            return formatMillisToGMT(milliseconds)
        }

        fun getExpiration(expiresTime: String): Long {
            try {
                return parseGMTToMillis(expiresTime)
            } catch (e: ParseException) {
                return -1
            }

        }

        fun getLastModified(lastModified: String): Long {
            try {
                return parseGMTToMillis(lastModified)
            } catch (e: ParseException) {
                return 0
            }

        }

        fun getCacheControl(cacheControl: String?, pragma: String?): String? {
            // first http1.1, second http1.0
            return cacheControl ?: pragma
        }

        @Throws(ParseException::class)
        fun parseGMTToMillis(gmtTime: String): Long {
            if (TextUtils.isEmpty(gmtTime)) return 0
            val formatter = SimpleDateFormat(FORMAT_HTTP_DATA, Locale.US)
            formatter.timeZone = GMT_TIME_ZONE
            val date = formatter.parse(gmtTime)
            return date.time
        }

        fun formatMillisToGMT(milliseconds: Long): String {
            val date = Date(milliseconds)
            val simpleDateFormat = SimpleDateFormat(FORMAT_HTTP_DATA, Locale.US)
            simpleDateFormat.timeZone = GMT_TIME_ZONE
            return simpleDateFormat.format(date)
        }
    }
}