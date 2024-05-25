package com.wildan.subs1intermediet.data.config

import com.google.gson.annotations.SerializedName
import com.wildan.subs1intermediet.data.library.BaseResponse

// Login
data class RequestLogin(
    val email: String,
    val password: String
)

data class ResponseLogin(
    @field:SerializedName("loginResult")
    val loginResult: ResultLogin
) : BaseResponse()

class ResultLogin(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("token")
    val token: String
)



// Register
data class RequestRegister(
    val name: String,
    val email: String,
    val password: String
)

class ResponseRegister: BaseResponse()




