package com.example.wildberriestravel.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.wildberriestravel.R
import com.example.wildberriestravel.databinding.FragmentSingleFlightBinding
import com.example.wildberriestravel.util.DateTimeFormatter
import com.example.wildberriestravel.viewmodel.FlightsViewModel

class SingleFlightFragment : Fragment() {
    private val viewModel by activityViewModels<FlightsViewModel>()

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.clearFlight()
            findNavController().navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSingleFlightBinding.inflate(inflater, container, false)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )

        viewModel.flight.observe(viewLifecycleOwner) { flight ->
            flight?.let {
                binding.price.text = it.price.toString()
                binding.track.text = getString(R.string.track, it.startCity, it.endCity)
                binding.serviceClass.text = it.serviceClass
                binding.dateDeparture.text = DateTimeFormatter.formatDate(it.startDate)
                binding.timeDeparture.text = DateTimeFormatter.formatTime(it.startDate)
                binding.cityDeparture.text =
                    getString(R.string.city_with_code, it.startLocationCode, it.startCity)
                binding.cityArrival.text =
                    getString(R.string.city_with_code, it.endLocationCode, it.endCity)
                binding.dateArrival.text = DateTimeFormatter.formatDate(it.endDate)
                binding.timeArrival.text = DateTimeFormatter.formatTime(it.endDate)

                // сервер присылает дату прибытия 01.01.0001,
                // отображается отрицательное время в пути
                binding.flightTime.text = getString(
                    R.string.flight_time,
                    DateTimeFormatter.countTimeFlight(it.startDate, it.endDate)
                )

                binding.buttonLike.isChecked = flight.likedByMe
                binding.buttonLike.setOnClickListener {
                    viewModel.like(flight)
                }
            }
        }

        binding.buttonBack.setOnClickListener {
            viewModel.clearFlight()
            findNavController().navigateUp()
        }

        return binding.root
    }

}