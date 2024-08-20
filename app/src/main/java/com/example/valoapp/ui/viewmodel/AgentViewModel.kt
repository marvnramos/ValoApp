package com.example.valoapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.valoapp.data.RetrofitInstance
import com.example.valoapp.data.models.AgentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgentViewModel : ViewModel() {
    private val _agentResponse = MutableLiveData<AgentResponse?>()
    val agentResponse: LiveData<AgentResponse?> = _agentResponse

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchAgent(uuid: String) {
        _isLoading.value = true
        RetrofitInstance.ApiClient.getAgentById(uuid).enqueue(object : Callback<AgentResponse> {
            override fun onResponse(
                call: Call<AgentResponse>,
                response: Response<AgentResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _agentResponse.value = response.body()
                } else {
                    _errorMessage.value = "Error: ${response.code()} ${response.message()}"
                }
            }

            override fun onFailure(call: Call<AgentResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Failed to fetch data: ${t.message}"
            }
        })
    }
}