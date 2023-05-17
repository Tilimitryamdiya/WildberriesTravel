package com.example.wildberriestravel.model

data class FlightModelState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val empty: Boolean = false,
    val refreshing: Boolean = false,
)
