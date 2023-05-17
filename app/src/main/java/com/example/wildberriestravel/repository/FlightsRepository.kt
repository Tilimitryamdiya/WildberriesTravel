package com.example.wildberriestravel.repository

import com.example.wildberriestravel.model.Flight

interface FlightsRepository {

    fun getAll(callback: FlightsCallback<List<Flight>>, startLocationCode: String)

    interface FlightsCallback<T> {
        fun onSuccess(value: T)
        fun onError(e: Exception)
    }
}