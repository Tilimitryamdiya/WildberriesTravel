package com.example.wildberriestravel.repository

import com.example.wildberriestravel.model.Flight
import com.example.wildberriestravel.model.FlightsList
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit


class FlightsRepositoryImpl: FlightsRepository {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()

    override fun getAll(
        callback: FlightsRepository.FlightsCallback<List<Flight>>,
        startLocationCode: String,
    ) {
        val startLocationCodeRequest = "{\"startLocationCode\":\"$startLocationCode\"}"
        val body = startLocationCodeRequest.toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder()
            .url(BASE_URL)
            .header("Accept", "application/json, text/plain, /")
            .header("Content-Type", "application/json")
            .post(body)
            .build()

        client.newCall(request)
            .enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val responseBody =
                            response.body?.string() ?: throw RuntimeException("body is null")
                        callback.onSuccess(
                            gson.fromJson(
                                responseBody,
                                FlightsList::class.java
                            ).flights
                        )
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(e)
                }
            })
    }

    companion object {
        private const val BASE_URL =
            "https://vmeste.wildberries.ru/api/avia-service/twirp/aviaapijsonrpcv1.WebAviaService/GetCheap"
    }
}
