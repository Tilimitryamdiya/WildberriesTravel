package com.example.wildberriestravel.repository

import android.util.Log
import com.example.wildberriestravel.api.ApiService
import com.example.wildberriestravel.dao.FlightDao
import com.example.wildberriestravel.entity.FlightEntity
import com.example.wildberriestravel.entity.toDto
import com.example.wildberriestravel.entity.toEntity
import com.example.wildberriestravel.error.ApiError
import com.example.wildberriestravel.error.NetworkError
import com.example.wildberriestravel.error.UnknownError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import javax.inject.Inject


class FlightsRepositoryImpl @Inject constructor(
    private val dao: FlightDao,
    private val apiService: ApiService,
) : FlightsRepository {

    override val data = dao.getAll()
        .map(List<FlightEntity>::toDto)
        .flowOn(Dispatchers.Default)

    override suspend fun getAll(
        startLocationCode: String,
    ) {
        try {
            val startLocationCodeRequest =
                "{\"startLocationCode\":\"$startLocationCode\"}"
                    .toRequestBody("application/json".toMediaTypeOrNull())
            val response = apiService.getFlights(startLocationCodeRequest)
            if (!response.isSuccessful) {
                throw ApiError(response.message())
            }
            val data = response.body()?.flights ?: throw ApiError(response.message())
            dao.insert(data.toEntity())
        }catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            Log.e("UnknownError", e.stackTraceToString())
            throw UnknownError
        }
    }

    override suspend fun refreshFlights(startLocationCode: String) {
        dao.removeAll()
        getAll(startLocationCode)
    }

    override suspend fun likeFlight(searchToken: String) {
        dao.likeById(searchToken)
    }
}
