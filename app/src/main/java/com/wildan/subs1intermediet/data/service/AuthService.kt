package com.wildan.subs1intermediet.data.service

import com.wildan.subs1intermediet.data.config.ResponseLogin
import com.wildan.subs1intermediet.data.config.ResponseRegister
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name")
        name: String,

        @Field("email")
        email: String,

        @Field("password")
        password: String
    ): ResponseRegister

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email")
        email: String,

        @Field("password")
        password: String
    ): ResponseLogin
}