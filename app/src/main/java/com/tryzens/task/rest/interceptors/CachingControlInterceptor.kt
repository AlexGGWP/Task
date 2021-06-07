package com.tryzens.task.rest.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class CachingControlInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val response = chain.proceed(builder.build())

        // Add Cache Control only for GET methods
        if (request.method == "GET") {
            // maybe application layer already set "no-cache" option. don't overwrite it in that case!
            if (!request.cacheControl.noCache) {
                val headers = response.headers
                val cacheControlHeaderValue = headers[CACHE_CONTROL]
                if (cacheControlHeaderValue != null) {
                    builder.header(CACHE_CONTROL, cacheControlHeaderValue)
                } else {
                    builder.header(CACHE_CONTROL, "only-if-cached")
                }
            }
        }
        return response
    }

    companion object {
        private const val CACHE_CONTROL = "Cache-Control"
    }
}