package com.example.wildberriestravel.api

import com.example.wildberriestravel.model.FlightsResponse
import okhttp3.RequestBody
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

    @POST("GetCheap")
    suspend fun getFlights(@Body startLocationCode: RequestBody): Response<FlightsResponse>
}