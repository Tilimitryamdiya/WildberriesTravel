package com.example.wildberriestravel.dto

data class Flight(
    val startDate: String,
    val endDate: String,
    val startLocationCode: String,
    val endLocationCode: String,
    val startCity: String,
    val endCity: String,
    val serviceClass: String,
    val price: Int,
    val searchToken: String,
    var likedByMe: Boolean,
)
