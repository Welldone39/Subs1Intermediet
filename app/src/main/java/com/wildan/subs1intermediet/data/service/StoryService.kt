package com.wildan.subs1intermediet.data.service

import com.wildan.subs1intermediet.data.config.ResponseAddStory
import com.wildan.subs1intermediet.data.config.ResponseStory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface StoryService {
    @GET("stories")
    suspend fun getAll(
        @Query("page")
        page: Int,

        @Query("size")
        size: Int,
    ):ResponseStory

    @Multipart
    @POST("stories")
    suspend fun upload(
        @Part
        photo: MultipartBody.Part,

        @Part("description")
        description: RequestBody

    ): ResponseAddStory

}