package com.example.valoapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valoapp.data.RetrofitInstance
import com.example.valoapp.data.models.agents.AgentsResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgentsViewModel : ViewModel() {
    private val _agentsResponse = MutableLiveData<AgentsResponse?>()
    val agentsResponse: LiveData<AgentsResponse?> = _agentsResponse

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        fetchAgents()
    }

    private fun fetchAgents() {
        viewModelScope.launch {
            try {
                val data = RetrofitInstance.ApiClient.getAgents()
                data.enqueue(object : Callback<AgentsResponse> {
                    override fun onResponse(
                        call: Call<AgentsResponse>,
                        response: Response<AgentsResponse>
                    ) {
                        _isLoading.value = false
                        if (response.isSuccessful) {
                            _agentsResponse.value = response.body()
                        } else {
                            _errorMessage.value =
                                "Doesn't work: ${response.code()} ${response.message()}"
                        }
                    }

                    override fun onFailure(call: Call<AgentsResponse>, t: Throwable) {
                        _isLoading.value = false
                        _errorMessage.value = "Failed to fetch data"
                    }
                })
            } catch (e: Exception) {
                _isLoading.value = false
                _errorMessage.value = "Failed to fetch data"
            }
        }
    }
}
