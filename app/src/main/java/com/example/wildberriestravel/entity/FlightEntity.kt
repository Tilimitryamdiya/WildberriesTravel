package com.example.wildberriestravel.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wildberriestravel.dto.Flight

@Entity
data class FlightEntity(

    val startDate: String,
    val endDate: String,
    val startLocationCode: String,
    val endLocationCode: String,
    val startCity: String,
    val endCity: String,
    val serviceClass: String,
    val price: Int,
    @PrimaryKey
    val searchToken: String,
    var likedByMe: Boolean = false,
) {
    fun toDto() = Flight(
        startDate = startDate,
        endDate = endDate,
        startLocationCode = startLocationCode,
        endLocationCode = endLocationCode,
        startCity = startCity,
        endCity = endCity,
        serviceClass = serviceClass,
        price = price,
        searchToken = searchToken,
        likedByMe = likedByMe
    )

    companion object {
        fun fromDto(flight: Flight) = FlightEntity(
            startDate = flight.startDate,
            endDate = flight.endDate,
            startLocationCode = flight.startLocationCode,
            endLocationCode = flight.endLocationCode,
            startCity = flight.startCity,
            endCity = flight.endCity,
            serviceClass = flight.serviceClass,
            price = flight.price,
            searchToken = flight.searchToken,
        )
    }
}

fun List<FlightEntity>.toDto(): List<Flight> = map(FlightEntity::toDto)
fun List<Flight>.toEntity(): List<FlightEntity> = map(FlightEntity::fromDto)