package com.wildan.subs1intermediet.ui.ViewModel

import android.net.Uri
import androidx.core.net.toFile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wildan.subs1intermediet.data.config.RequestAddStory
import com.wildan.subs1intermediet.data.config.ResponseAddStory
import com.wildan.subs1intermediet.data.library.ApiResponse
import com.wildan.subs1intermediet.data.repository.StoryRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class ViewModelAddStory(private val repository: StoryRepository): ViewModel() {

    private val _resultAddStory = MutableLiveData<ApiResponse<ResponseAddStory>>()
    val resultAddStory: LiveData<ApiResponse<ResponseAddStory>> by  lazy { _resultAddStory }

    fun uploadStory(photo: Uri, description: String) {
        val photoFile = photo.toFile()
        val requestImageFile = photoFile.asRequestBody("image/*".toMediaType())
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val multiPartBody = MultipartBody.Part.createFormData("photo", photoFile.name, requestImageFile)

        viewModelScope.launch {
            val dto = RequestAddStory(multiPartBody, requestBody)
            repository.upload(dto).collect{ it ->
            _resultAddStory.value = it }
        }
    }
}