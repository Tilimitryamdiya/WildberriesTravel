package com.example.wildberriestravel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wildberriestravel.dto.Flight
import com.example.wildberriestravel.model.FlightModelState
import com.example.wildberriestravel.repository.FlightsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightsViewModel @Inject constructor(
    private val repository: FlightsRepository
) : ViewModel() {

    val data = repository.data

    private var _dataState = MutableLiveData(FlightModelState())
    val dataState: LiveData<FlightModelState>
        get() = _dataState

    private val _flight: MutableLiveData<Flight?> = MutableLiveData(null)
    val flight: LiveData<Flight?>
        get() = _flight

    fun loadFlights(startLocationCode: String) {
        viewModelScope.launch {
            try {
                _dataState.postValue(FlightModelState(loading = true))
                repository.getAll(startLocationCode)
                _dataState.postValue(FlightModelState())
            } catch (e: Exception) {
                _dataState.value = FlightModelState(error = true)
            }

        }
    }

    fun putFlight(flight: Flight) {
        _flight.value = flight
    }

    fun clearFlight() {
        _flight.value = null
    }

    fun like(flight: Flight) {
        viewModelScope.launch {
            repository.likeFlight(flight.searchToken)
        }
    }
}
