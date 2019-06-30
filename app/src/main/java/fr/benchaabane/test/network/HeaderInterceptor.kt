package fr.benchaabane.test.network

import fr.benchaabane.test.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(chain.request().newBuilder()
        .addHeader(ACCEPT_KEY, ACCEPT_VALUE)
        .addHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE)
        .addHeader(PARSE_APP_ID, BuildConfig.PARSE_APP_ID)
        .addHeader(PARSE_API_KEY, BuildConfig.PARSE_API_KEY)
        .build())
}

const val ACCEPT_KEY = "Accept"
const val ACCEPT_VALUE = "application/json"
const val CONTENT_TYPE_KEY = "Content-Type"
const val CONTENT_TYPE_VALUE = "application/json"
const val PARSE_APP_ID = "X-Parse-Application-Id"
const val PARSE_API_KEY = "X-Parse-REST-API-Key"
