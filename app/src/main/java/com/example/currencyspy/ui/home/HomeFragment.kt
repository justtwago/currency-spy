package com.example.currencyspy.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.currencyspy.databinding.FragmentHomeBinding
import com.example.currencyspy.ui.details.model.asRateDetailsViewState
import com.example.currencyspy.ui.home.list.adapter.CurrencyRatesAdapter
import com.example.currencyspy.ui.home.list.adapter.LoaderAdapter
import com.example.domain.CurrencyRate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private val currencyRatesAdapter = CurrencyRatesAdapter(
        doOnRateClicked = ::navigateToRateDetails
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderViews()
        initObservers()
        setupListeners()
    }

    private fun renderViews() {
        binding.currencyRates.adapter = currencyRatesAdapter.withLoadStateFooter(
            footer = LoaderAdapter(doOnRetryClicked = currencyRatesAdapter::retry)
        )
    }

    private fun setupListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshRates()
        }
        currencyRatesAdapter.addLoadStateListener { loadSate ->
            renderInitialLoadingState(loadSate)
            renderErrorState(loadSate)
            renderSwipeToRefreshState(loadSate)
        }
    }

    private fun renderInitialLoadingState(loadSate: CombinedLoadStates) {
        val isInitialState = currencyRatesAdapter.itemCount == 0
        val isLoading = loadSate.refresh is LoadState.Loading

        binding.loadingLayout.root.isVisible = isLoading && isInitialState
    }

    private fun renderErrorState(loadSate: CombinedLoadStates) {
        val errorLayout = binding.errorLayout.root
        val loadingAfterError = loadSate.refresh is LoadState.Loading && errorLayout.isVisible
        val isErrorOccurred = loadSate.refresh is LoadState.Error

        errorLayout.isVisible = isErrorOccurred || loadingAfterError
    }

    private fun renderSwipeToRefreshState(loadSate: CombinedLoadStates) {
        if (loadSate.refresh !is LoadState.Loading) binding.swipeRefresh.isRefreshing = false
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.currencyRates.collectLatest(currencyRatesAdapter::submitData)
        }
    }

    private fun navigateToRateDetails(clickedCurrencyRate: CurrencyRate) {
        val currencyDetailsViewState = clickedCurrencyRate.asRateDetailsViewState()
        val direction = HomeFragmentDirections.actionOpenDetails(currencyDetailsViewState)

        findNavController().navigate(direction)
    }
}