package com.mansoul.common.http.exception

import android.util.SparseArray

/**
 * 自定义错误码辅助类
 *
 * @author Mansoul
 * @date 2017/9/7 16:23
 */
object ExceptionCode {

    /**
     * 网络错误
     */
    const val NETWORD_ERROR = 1000
    /**
     * 服务器连接超时
     */
    const val CONNECT_TIMEOUT = 1001
    /**
     * 服务器响应超时
     */
    const val SOCKET_TIMEOUT = 1002
    /**
     * 未知的主机域名
     */
    const val UNKNOWN_HOST = 1003
    /**
     * 不支持的编码格式异常
     */
    const val UNSUPPORTED_ENCODING = 1004
    /**
     * URL异常
     */
    const val MALFORMED_URL = 1006
    /**
     * 证书出错
     */
    const val SSL_ERROR = 1007
    /**
     * 证书未找到
     */
    const val SSL_NOT_FOUND = 1008
    /**
     * 类型转换异常
     */
    const val CLASS_CAST = 1009
    /**
     * 解析错误
     */
    const val PARSE_ERROR = 1010
    /**
     * 服务端返回数据格式异常
     */
    const val FORMAT_ERROR = 1011
    /**
     * 数据出现空值
     */
    const val DATA_NULL = 1012
    /**
     * 日期格式解析异常
     */
    const val DATE_PARSE = 1013
    /**
     * 未知错误
     */
    val UNKNOWN = 9999

    private val CODE_MAP = SparseArray<String>()

    init {
        CODE_MAP.put(NETWORD_ERROR, "网络错误")
        CODE_MAP.put(CONNECT_TIMEOUT, "服务器连接超时")
        CODE_MAP.put(SOCKET_TIMEOUT, "服务器响应超时")
        CODE_MAP.put(UNKNOWN_HOST, "未知的主机域名")
        CODE_MAP.put(UNSUPPORTED_ENCODING, "不支持的编码格式异常")
        CODE_MAP.put(MALFORMED_URL, "URL异常")
        CODE_MAP.put(SSL_ERROR, "证书验证失败")
        CODE_MAP.put(SSL_NOT_FOUND, "证书路径没找到")
        CODE_MAP.put(CLASS_CAST, "类型转换异常")
        CODE_MAP.put(DATE_PARSE, "类型转换异常")
        CODE_MAP.put(PARSE_ERROR, "数据解析异常")
        CODE_MAP.put(FORMAT_ERROR, "服务端返回数据格式异常")
        CODE_MAP.put(DATA_NULL, "数据出现空值")
        CODE_MAP.put(DATE_PARSE, "日期格式解析异常")
        CODE_MAP.put(UNKNOWN, "未知错误")
    }

    operator fun get(code: Int): String {
        return CODE_MAP.get(code)
    }

}