package com.example.valoapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.valoapp.data.RetrofitInstance
import com.example.valoapp.data.models.maps.MapResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapViewModel : ViewModel() {
    private val _mapResponse = MutableLiveData<MapResponse?>()
    val mapResponse: LiveData<MapResponse?> = _mapResponse

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchMap(uuid: String) {
        _isLoading.value = true
        RetrofitInstance.ApiClient.getMapById(uuid).enqueue(object : Callback<MapResponse> {
            override fun onResponse(
                call: Call<MapResponse>,
                response: Response<MapResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _mapResponse.value = response.body()
                } else {
                    _errorMessage.value = "Error: ${response.code()} ${response.message()}"
                }
            }

            override fun onFailure(call: Call<MapResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Failed to fetch data: ${t.message}"
            }
        })
    }
}