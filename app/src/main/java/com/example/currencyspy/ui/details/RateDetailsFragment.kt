package com.example.currencyspy.ui.details

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.currencyspy.R
import com.example.currencyspy.databinding.FragmentRateDetailsBinding
import com.example.currencyspy.ui.details.model.CurrencyRateUiModel
import com.example.currencyspy.utils.observeNotNull
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RateDetailsFragment : Fragment() {
    private lateinit var binding: FragmentRateDetailsBinding
    private val viewModel: RateDetailsViewModel by viewModels()
    private val args: RateDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRateDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        setListeners()
        updateRateDetails()
    }

    private fun initObservers() {
        viewModel.currencyRateLiveData.observeNotNull(viewLifecycleOwner, ::renderRateDetails)
    }

    private fun renderRateDetails(rate: CurrencyRateUiModel) = with(binding) {
        baseCurrency.text = getString(R.string.currency_rate_base_currency, rate.baseCurrencyCode)
        currencyRate.text = getString(
            R.string.currency_rate_price,
            rate.rate.toString(),
            rate.currencyCode
        )
        date.text = rate.formattedDate
    }

    private fun setListeners() = with(binding) {
        backButton.setOnClickListener { findNavController().popBackStack() }
        rateCard.setOnClickListener {
            viewModel.copyRate()
            showSuccessCopySnackbar()
        }
    }

    private fun showSuccessCopySnackbar() {
        Snackbar.make(
            binding.coordinatorLayout,
            R.string.currency_rate_copy_snackbar,
            1000
        ).show()
    }

    private fun updateRateDetails() {
        viewModel.updateCurrencyRate(args.currencyRateDetails)
    }
}