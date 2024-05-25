package com.wildan.subs1intermediet.Module

import com.wildan.subs1intermediet.data.library.HeaderInterceptor
import com.wildan.subs1intermediet.data.service.AuthService
import com.wildan.subs1intermediet.data.service.StoryService
import com.wildan.subs1intermediet.utils.ManagerPreference
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val baseUrl = "https://story-api.dicoding.dev/v1/"
val moduleNetwork = module {
    single {
        return@single OkHttpClient.Builder()
            .addInterceptor(getHeaderInterceptor(get()))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single { providerAuthService(get()) }
    single { providerStoryService(get()) }

}

private fun getHeaderInterceptor(managerPreference: ManagerPreference): Interceptor {
    val headers = HashMap<String, String>()
    headers["Content-Type"] = "application/json"
    return HeaderInterceptor(headers, managerPreference)

}

fun providerAuthService(retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)
fun providerStoryService(retrofit: Retrofit): StoryService = retrofit.create(StoryService::class.java)
