package com.wildan.subs1intermediet.data.repository

import com.google.gson.Gson
import com.wildan.subs1intermediet.Module.moduleNetwork
import com.wildan.subs1intermediet.Module.modulePreference
import com.wildan.subs1intermediet.data.config.RequestLogin
import com.wildan.subs1intermediet.data.config.RequestRegister
import com.wildan.subs1intermediet.data.config.ResponseLogin
import com.wildan.subs1intermediet.data.config.ResponseRegister
import com.wildan.subs1intermediet.data.library.ApiResponse
import com.wildan.subs1intermediet.data.service.AuthService
import com.wildan.subs1intermediet.utils.ManagerPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.context.unloadKoinModules
import org.koin.core.context.loadKoinModules
import retrofit2.HttpException


interface RepositoryAuth {
    fun login(dto: RequestLogin): Flow<ApiResponse<ResponseLogin>>
    fun register(dto: RequestRegister): Flow<ApiResponse<ResponseRegister>>
}

class AuthRepositoryImpl(private val api: AuthService, private val prefs: ManagerPreference) : RepositoryAuth {
    override fun login(dto: RequestLogin): Flow<ApiResponse<ResponseLogin>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.login(dto.email, dto.password)
            if (!response.error) {
                val userItem = response.loginResult
                prefs.setPrefLogin(userItem)

                reloadModule()
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Error(response.message))
            }
        } catch (e: Exception) {
            if (e is HttpException) {
                val errorMessage =
                    Gson().fromJson(e.response()?.errorBody()?.string(), ResponseLogin::class.java)
                emit(ApiResponse.Error(errorMessage.message))
            } else {
                e.printStackTrace()
                emit(ApiResponse.Error("Unknown error"))
            }
        }

    }

    override fun register(dto: RequestRegister): Flow<ApiResponse<ResponseRegister>> = flow {
        try {
            emit(ApiResponse.Loading)
            val response = api.register(dto.name, dto.email, dto.password)
            if (!response.error) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Error(response.message))
            }
        } catch (e:Exception) {
            if (e is HttpException) {
                val errorMessage = Gson().fromJson(
                    e.response()?.errorBody()?.string(),
                    ResponseRegister::class.java
                )
                emit(ApiResponse.Error(errorMessage.message))
            } else {
                e.printStackTrace()
                emit(ApiResponse.Error("Unknown error"))
            }

        }

    }
    private fun reloadModule() {
        unloadKoinModules(modulePreference)
        loadKoinModules(modulePreference)
        unloadKoinModules(moduleNetwork)
        loadKoinModules(moduleNetwork)
    }

}