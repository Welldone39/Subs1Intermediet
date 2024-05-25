package com.wildan.subs1intermediet.ui.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wildan.subs1intermediet.data.config.GetParamStory
import com.wildan.subs1intermediet.data.config.ResponseStory
import com.wildan.subs1intermediet.data.library.ApiResponse
import com.wildan.subs1intermediet.data.repository.StoryRepository
import kotlinx.coroutines.launch

class ViewModelStory(private val repository: StoryRepository) : ViewModel() {
    private val page: Int = 1
    private val size: Int = 5

    private val _story = MutableLiveData<ApiResponse<ResponseStory>>()
    val story: LiveData<ApiResponse<ResponseStory>> by lazy { _story }

    fun get() {
        viewModelScope.launch {
            repository.getAll(GetParamStory(page, size)).collect { it ->
                _story.value = it
            }
        }
    }
}