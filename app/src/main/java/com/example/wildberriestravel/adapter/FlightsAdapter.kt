package com.example.wildberriestravel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wildberriestravel.databinding.CardFlightBinding
import com.example.wildberriestravel.model.Flight
import com.example.wildberriestravel.util.DateTimeFormatter

class FlightsAdapter(
    private val listener: AdapterListener,
) : ListAdapter<Flight, FlightsViewHolder>(FlightsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardFlightBinding.inflate(inflater, parent, false)
        return FlightsViewHolder(binding, listener)
    }

    override fun onBindViewHolder(
        holder: FlightsViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            payloads.forEach {
                (it as? Payload)?.let { payload ->
                    holder.bind(payload)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: FlightsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}

class FlightsViewHolder(
    private val binding: CardFlightBinding,
    private val listener: AdapterListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(flight: Flight) = with(binding) {
        price.text = flight.price.toString()
        dateDeparture.text = DateTimeFormatter.formatDate(flight.startDate)
        timeDeparture.text = DateTimeFormatter.formatTime(flight.startDate)
        cityDeparture.text = flight.startCity
        dateArrival.text = DateTimeFormatter.formatDate(flight.endDate)
        timeArrival.text = DateTimeFormatter.formatTime(flight.endDate)
        cityArrival.text = flight.endCity

        buttonLike.isChecked = flight.likedByMe
        buttonLike.setOnClickListener { listener.onLike(flight) }

        card.setOnClickListener { listener.onFlight(flight) }
    }

    fun bind(payload: Payload) {
        payload.likeByMe?.let {
            binding.buttonLike.isChecked = it
        }
    }
}

class FlightsDiffCallback : DiffUtil.ItemCallback<Flight>() {
    override fun areItemsTheSame(oldItem: Flight, newItem: Flight): Boolean {
        return oldItem.searchToken == newItem.searchToken
    }

    override fun areContentsTheSame(oldItem: Flight, newItem: Flight): Boolean {
        return oldItem == newItem
    }
}

interface AdapterListener {
    fun onLike(flight: Flight)
    fun onFlight(flight: Flight)
}

data class Payload(
    val likeByMe: Boolean? = null,
)