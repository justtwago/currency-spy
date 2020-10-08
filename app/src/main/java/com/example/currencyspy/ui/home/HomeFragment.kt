package com.example.currencyspy.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.currencyspy.databinding.FragmentHomeBinding
import com.example.currencyspy.ui.home.list.CurrencyRatesAdapter
import com.example.currencyspy.utils.observeNotNull
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val currencyRatesAdapter = CurrencyRatesAdapter()

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
    }

    private fun renderViews() {
        binding.currencyRates.adapter = currencyRatesAdapter
        viewModel.currencyRates.observeNotNull(viewLifecycleOwner) {
            lifecycleScope.launch { currencyRatesAdapter.submitData(it) }
        }
    }
}