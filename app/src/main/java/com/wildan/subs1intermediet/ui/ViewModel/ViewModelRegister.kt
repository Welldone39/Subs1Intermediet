package com.wildan.subs1intermediet.ui.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wildan.subs1intermediet.data.config.RequestRegister
import com.wildan.subs1intermediet.data.config.ResponseRegister
import com.wildan.subs1intermediet.data.library.ApiResponse
import com.wildan.subs1intermediet.data.repository.RepositoryAuth
import kotlinx.coroutines.launch

class ViewModelRegister(private val repository: RepositoryAuth) : ViewModel() {
    private val _registerResult = MutableLiveData<ApiResponse<ResponseRegister>>()
    val registerResult : LiveData<ApiResponse<ResponseRegister>> by lazy { _registerResult }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            repository.register(RequestRegister(name, email, password)).collect{
                _registerResult.value = it
            }
        }
    }
}