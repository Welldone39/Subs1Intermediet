package com.wildan.subs1intermediet.data.repository

import com.google.gson.Gson
import com.wildan.subs1intermediet.data.config.GetParamStory
import com.wildan.subs1intermediet.data.config.RequestAddStory
import com.wildan.subs1intermediet.data.config.ResponseAddStory
import com.wildan.subs1intermediet.data.config.ResponseStory
import com.wildan.subs1intermediet.data.library.ApiResponse
import com.wildan.subs1intermediet.data.service.StoryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

interface StoryRepository {
    fun getAll(dto: GetParamStory): Flow<ApiResponse<ResponseStory>>
    fun upload(dto: RequestAddStory): Flow<ApiResponse<ResponseAddStory>>
}

class StoryRepositoryImpl(private val api: StoryService): StoryRepository {
    override fun getAll(dto: GetParamStory): Flow<ApiResponse<ResponseStory>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.getAll(dto.page, dto.size)
            if (!response.error) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Error(response.message))
            }
        } catch (e: Exception) {
            if (e is HttpException) {
                val errorMessage = Gson().fromJson(e.response()?.errorBody()?.string(), ResponseStory::class.java)
                emit(ApiResponse.Error(errorMessage.message))
            } else {
                e.printStackTrace()
                emit(ApiResponse.Error("Unknown error"))
            }
        }
    }

    override fun upload(dto: RequestAddStory): Flow<ApiResponse<ResponseAddStory>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.upload(dto.photo, dto.description)
            if(!response.error) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Error(response.message))
            }
        } catch (e: Exception) {
            if(e is HttpException) {
                val messageError = Gson().fromJson(e.response()?.errorBody()?.string(), ResponseAddStory::class.java)
                emit(ApiResponse.Error(messageError.message))
            } else {
                e.printStackTrace()
                emit(ApiResponse.Error("Unknown error"))
            }
        }
    }
}