package com.tryzens.task.rest.interceptors

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ForceNoCacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        if (request.url.pathSegments.contains("orders")) {
            builder.cacheControl(CacheControl.FORCE_NETWORK)
        }
        return chain.proceed(builder.build())
    }
}