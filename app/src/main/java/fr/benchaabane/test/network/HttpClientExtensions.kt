package fr.benchaabane.test.network

import fr.benchaabane.test.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

fun OkHttpClient.Builder.buildHttpClient(cache: Cache,
                                         networkConfig: NetworkConfig,
                                         headerInterceptor: HeaderInterceptor? = null): OkHttpClient {
    cache(cache)
    connectTimeout(networkConfig.connectionTimeOut, TimeUnit.MILLISECONDS)
    readTimeout(networkConfig.socketTimeOut, TimeUnit.MILLISECONDS)
    headerInterceptor?.let { addNetworkInterceptor(it) }

    if (BuildConfig.LOG_ENABLE) {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        addInterceptor(logInterceptor)
    }

    if (BuildConfig.DEBUG) {
        trustingAllHosts(this)
    }

    return build()
}

private fun trustingAllHosts(httpClientBuilder: OkHttpClient.Builder) {
    try {
        val trustAllCertsManager = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }
        })
        // install the all trusting manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCertsManager, SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory

        httpClientBuilder.sslSocketFactory(sslSocketFactory)
        httpClientBuilder.hostnameVerifier { _, _ -> true }

    } catch (exception: Exception) {
        Timber.e(exception, "HttpClientUtils Trusting All Hosts Error :${exception.message}")
    }
}

