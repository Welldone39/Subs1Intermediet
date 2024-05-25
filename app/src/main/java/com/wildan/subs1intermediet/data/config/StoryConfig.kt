package com.wildan.subs1intermediet.data.config

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.wildan.subs1intermediet.data.library.BaseResponse
import kotlinx.parcelize.Parcelize
import okhttp3.MultipartBody
import okhttp3.RequestBody

//Story

data class ResponseStory(
    @field: SerializedName("listStory")
    val listStory: List<ItemStory>,
) : BaseResponse()

@Parcelize

data class ItemStory(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("photoUrl")
    val photoUrl: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("lon")
    val lon: Float,

    @field:SerializedName("lat")
    val lat: Float

): Parcelable

data class GetParamStory(
    val page: Int,
    val size: Int,
)

class ResponseAddStory(): BaseResponse()

data class RequestAddStory(
    val photo: MultipartBody.Part,
    val description: RequestBody,
)