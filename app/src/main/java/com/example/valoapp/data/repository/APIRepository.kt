package com.example.valoapp.data.repository

import com.example.valoapp.data.models.Agents
import retrofit2.http.GET
import retrofit2.Call

interface APIRepository{
    @GET("agents")
    fun getAgents(): Call<Agents>
}