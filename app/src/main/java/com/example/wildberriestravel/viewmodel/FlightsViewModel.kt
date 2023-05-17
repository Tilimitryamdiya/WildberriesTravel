package com.example.wildberriestravel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wildberriestravel.model.Flight
import com.example.wildberriestravel.model.FlightModelState
import com.example.wildberriestravel.repository.FlightsRepository
import com.example.wildberriestravel.repository.FlightsRepositoryImpl
import kotlinx.coroutines.flow.*

class FlightsViewModel: ViewModel() {

    private val repository: FlightsRepository = FlightsRepositoryImpl()

    private val _data: MutableStateFlow<List<Flight>?> = MutableStateFlow(null)
    val data: Flow<List<Flight>?>
        get() = _data.asStateFlow()

    private var _dataState = MutableLiveData(FlightModelState())
    val dataState: LiveData<FlightModelState>
        get() = _dataState

    private val _flight: MutableLiveData<Flight?> = MutableLiveData(null)
    val flight: LiveData<Flight?>
        get() = _flight

    fun loadFlights(startLocationCode: String) {
        _dataState.postValue(FlightModelState(loading = true))
        repository.getAll(object : FlightsRepository.FlightsCallback<List<Flight>> {

            override fun onSuccess(value: List<Flight>) {
                _data.value = value
                _dataState.postValue(FlightModelState(empty = value.isEmpty()))
            }

            override fun onError(e: Exception) {
                _dataState.postValue(FlightModelState(error = true))
            }

        }, startLocationCode)
    }

    fun putFlight(flight: Flight) {
        _flight.value = flight
    }

    fun clearFlight() {
        _flight.value = null
    }

    fun like(flight: Flight) {

    }
}
