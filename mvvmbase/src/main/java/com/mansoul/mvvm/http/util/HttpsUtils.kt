package com.mansoul.mvvm.http.util

/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import com.orhanobut.logger.Logger
import java.io.IOException
import java.io.InputStream
import java.security.*
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 *
 * 描述：Https相关的工具类
 * 作者： zhouyou<br></br>
 * 日期： 2017/5/15 16:55 <br></br>
 * 版本： v1.0<br></br>
 */
object HttpsUtils {

    const val TAG = "HttpsUtils"

    class SSLParams {
        var sSLSocketFactory: SSLSocketFactory? = null
        var trustManager: X509TrustManager? = null
    }

    fun getSslSocketFactory(bksFile: InputStream, password: String, certificates: Array<InputStream>): SSLParams {
        val sslParams = SSLParams()
        try {
            val keyManagers = prepareKeyManager(bksFile, password)
            val trustManagers = prepareTrustManager(*certificates)
            val sslContext = SSLContext.getInstance("TLS")

            var trustManager: X509TrustManager? = null

            if (trustManagers != null) {
                val chooseTrustManager = chooseTrustManager(trustManagers)
                if (chooseTrustManager != null)
                    trustManager = SafeTrustManager(chooseTrustManager)
            } else {
                trustManager = UnSafeTrustManager()
            }
            sslContext.init(
                keyManagers,
                arrayOf(trustManager),
                SecureRandom()
            )
            sslParams.sSLSocketFactory = sslContext.socketFactory
            sslParams.trustManager = trustManager
            return sslParams
        } catch (e: NoSuchAlgorithmException) {
            throw AssertionError(e)
        } catch (e: KeyManagementException) {
            throw AssertionError(e)
        } catch (e: KeyStoreException) {
            throw AssertionError(e)
        }

    }

    private fun prepareTrustManager(vararg certificates: InputStream): Array<TrustManager>? {
        if (certificates.size <= 0) {
            return null
        }
        try {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null)
            var index = 0
            for (certificate in certificates) {
                val certificateAlias = Integer.toString(index++)
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate))
                try {
                    certificate?.close()
                } catch (e: IOException) {
                    Logger.e(TAG, e)
                }

            }
            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)
            return trustManagerFactory.trustManagers
        } catch (e: NoSuchAlgorithmException) {
            Logger.e(TAG, e)
        } catch (e: CertificateException) {
            Logger.e(TAG, e)
        } catch (e: KeyStoreException) {
            Logger.e(TAG, e)
        } catch (e: Exception) {
            Logger.e(TAG, e)
        }

        return null
    }

    private fun prepareKeyManager(bksFile: InputStream?, password: String?): Array<KeyManager>? {
        try {
            if (bksFile == null || password == null) {
                return null
            }
            val keyStore = KeyStore.getInstance("BKS")
            keyStore.load(bksFile, password.toCharArray())
            val keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
            keyManagerFactory.init(keyStore, password.toCharArray())
            return keyManagerFactory.keyManagers
        } catch (e: KeyStoreException) {
            Logger.e(TAG, e)
        } catch (e: NoSuchAlgorithmException) {
            Logger.e(TAG, e)
        } catch (e: UnrecoverableKeyException) {
            Logger.e(TAG, e)
        } catch (e: CertificateException) {
            Logger.e(TAG, e)
        } catch (e: IOException) {
            Logger.e(TAG, e)
        } catch (e: Exception) {
            Logger.e(TAG, e)
        }

        return null
    }

    private fun chooseTrustManager(trustManagers: Array<TrustManager>): X509TrustManager? {
        for (trustManager in trustManagers) {
            if (trustManager is X509TrustManager) {
                return trustManager
            }
        }
        return null
    }

    private class UnSafeTrustManager : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    }

    private class SafeTrustManager @Throws(NoSuchAlgorithmException::class, KeyStoreException::class)
    constructor(private val localTrustManager: X509TrustManager) : X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate?> {
            return arrayOfNulls(0)
        }

        private val defaultTrustManager: X509TrustManager?

        init {
            val managerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            managerFactory.init(null as KeyStore?)
            defaultTrustManager = chooseTrustManager(managerFactory.trustManagers)
        }

        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {

        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            try {
                defaultTrustManager!!.checkServerTrusted(chain, authType)
            } catch (ce: CertificateException) {
                localTrustManager.checkServerTrusted(chain, authType)
            }

        }
    }
}
