package com.example.valoapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.valoapp.data.RetrofitInstance
import com.example.valoapp.data.models.maps.MapsResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel: ViewModel() {
    private val _mapsResponse = MutableLiveData<MapsResponse?>()
    val mapsResponse: LiveData<MapsResponse?> = _mapsResponse

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        fetchMaps()
    }

    private fun fetchMaps(){
        viewModelScope.launch {
            try {
                val data = RetrofitInstance.ApiClient.getMaps()
                data.enqueue(object : Callback<MapsResponse>{
                    override fun onResponse(
                        call: Call<MapsResponse>,
                        response: Response<MapsResponse>
                    ){
                        _isLoading.value = false
                        if(response.isSuccessful){
                            _mapsResponse.value = response.body()
                        }else{
                            _errorMessage.value =
                                "Doesn't work: ${response.code()} ${response.message()}"
                        }
                    }

                    override fun onFailure(call: Call<MapsResponse>, t: Throwable) {
                        _isLoading.value = false
                        _errorMessage.value = "Failed to fetch data"
                    }
                })

            }catch (e: Exception){
                _isLoading.value = false
                _errorMessage.value = e.message
            }
        }
    }
}