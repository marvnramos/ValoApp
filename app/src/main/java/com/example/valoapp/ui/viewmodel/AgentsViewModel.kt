package com.example.valoapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valoapp.data.RetrofitInstance
import com.example.valoapp.data.models.Agents
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgentsViewModel : ViewModel() {
    private val _agents = MutableLiveData<Agents?>()
    val agents: LiveData<Agents?> = _agents

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
                data.enqueue(object : Callback<Agents> {
                    override fun onResponse(call: Call<Agents>, response: Response<Agents>) {
                        _isLoading.value = false
                        if (response.isSuccessful) {
                            _agents.value = response.body()
                        } else {
                            _errorMessage.value = "Doesn't work: ${response.code()} ${response.message()}"
                        }
                    }

                    override fun onFailure(call: Call<Agents>, t: Throwable) {
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
