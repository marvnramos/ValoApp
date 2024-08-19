package com.example.valoapp.data


import com.example.valoapp.BuildConfig
import com.example.valoapp.data.repository.APIRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = BuildConfig.API_BASE_URL

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val ApiClient: APIRepository by lazy {
        retrofit.create(APIRepository::class.java)
    }
}
