package com.wildan.subs1intermediet.ui.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wildan.subs1intermediet.data.config.RequestLogin
import com.wildan.subs1intermediet.data.config.ResponseLogin
import com.wildan.subs1intermediet.data.library.ApiResponse
import com.wildan.subs1intermediet.data.repository.RepositoryAuth
import kotlinx.coroutines.launch

class ViewModelLogin(private val repository: RepositoryAuth): ViewModel() {
    private val _loginResult = MutableLiveData<ApiResponse<ResponseLogin>>()
    val loginResult: LiveData<ApiResponse<ResponseLogin>> by lazy { _loginResult }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(RequestLogin(email, password)).collect{
                _loginResult.value = it
            }
        }
    }

}