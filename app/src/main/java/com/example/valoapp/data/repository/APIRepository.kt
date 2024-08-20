package com.example.valoapp.data.repository

import com.example.valoapp.data.models.agents.AgentResponse
import com.example.valoapp.data.models.agents.AgentsResponse
import com.example.valoapp.data.models.maps.MapResponse
import com.example.valoapp.data.models.maps.MapsResponse
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path

interface APIRepository{
    @GET("agents?isPlayableCharacter=true")
    fun getAgents(): Call<AgentsResponse>
    @GET("agents/{uuid}")
    fun getAgentById(@Path("uuid") uuid: String): Call<AgentResponse>
    @GET("maps")
    fun getMaps(): Call<MapsResponse>
    @GET("agents/{uuid}")
    fun getMapById(@Path("uuid") uuid: String): Call<MapResponse>
}