package com.wildan.subs1intermediet.data.library

import android.preference.PreferenceManager
import com.wildan.subs1intermediet.utils.ManagerPreference
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class HeaderInterceptor(
    private val requestHeaders:HashMap<String, String>,
    private val managerPreference: ManagerPreference
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        mapRequestHeaders()

        val request = mapHeaders(chain)

        return chain.proceed(request)
    }

    private fun mapHeaders(chain: Interceptor.Chain): Request {
        val original = chain.request()

        val requestBuilder = original.newBuilder()

        for ((key, value) in requestHeaders) {
            requestBuilder.addHeader(key, value)
        }
        return requestBuilder.build()

    }

    private fun mapRequestHeaders() {
        val token = managerPreference.getToken
        requestHeaders["Authorization"] = "Bearer $token"
    }
}