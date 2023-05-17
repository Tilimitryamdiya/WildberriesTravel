package com.example.wildberriestravel.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.wildberriestravel.R
import com.example.wildberriestravel.adapter.AdapterListener
import com.example.wildberriestravel.adapter.FlightsAdapter
import com.example.wildberriestravel.databinding.FragmentFlightsListBinding
import com.example.wildberriestravel.model.Flight
import com.example.wildberriestravel.viewmodel.FlightsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FlightsListFragment : Fragment() {

    private val startLocationCode = "LED"
    private val viewModel by activityViewModels<FlightsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFlightsListBinding.inflate(inflater, container, false)


        val adapter = FlightsAdapter(object : AdapterListener {
            override fun onLike(flight: Flight) {
                viewModel.like(flight)
            }

            override fun onFlight(flight: Flight) {
                viewModel.putFlight(flight)
                findNavController().navigate(
                    R.id.action_flightsListFragment_to_singleFlightFragment
                )
            }

        })
        binding.listContainer.adapter = adapter
        viewModel.loadFlights(startLocationCode)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collectLatest {
                    adapter.submitList(it)
                }
            }
        }

        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            binding.progress.isVisible = state.loading
            binding.errorGroup.isVisible = state.error
            binding.emptyText.isVisible = state.empty
        }

        binding.retryButton.setOnClickListener {
            viewModel.loadFlights(startLocationCode)
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadFlights(startLocationCode)
            binding.swipeRefresh.isRefreshing = false
        }

        return binding.root
    }

}