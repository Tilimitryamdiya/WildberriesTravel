package com.example.wildberriestravel.repository

import com.example.wildberriestravel.dto.Flight
import kotlinx.coroutines.flow.Flow

interface FlightsRepository {
    val data: Flow<List<Flight>>
    suspend fun getAll(startLocationCode: String)
    suspend fun refreshFlights(startLocationCode: String)
    suspend fun likeFlight(searchToken: String)

}