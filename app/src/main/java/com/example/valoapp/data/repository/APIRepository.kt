package com.example.valoapp.data.repository

import com.example.valoapp.data.models.AgentResponse
import com.example.valoapp.data.models.AgentsResponse
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path

interface APIRepository{
    @GET("agents?isPlayableCharacter=true")
    fun getAgents(): Call<AgentsResponse>
    @GET("agents/{uuid}")
    fun getAgentById(@Path("uuid") uuid: String): Call<AgentResponse>
}