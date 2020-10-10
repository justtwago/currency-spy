package com.example.currencyspy.ui.home.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyspy.R
import com.example.currencyspy.databinding.ItemCurrencyRateBinding
import com.example.currencyspy.databinding.ItemHeaderBinding
import java.time.LocalDate

private const val HEADER_TYPE = 1
private const val RATE_TYPE = 2

class CurrencyRatesAdapter :
    PagingDataAdapter<CurrencyRateItem, RecyclerView.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_TYPE -> HeaderViewHolder.create(parent)
            RATE_TYPE -> RateViewHolder.create(parent)
            else -> error("Unsupported item type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CurrencyRateItem.Header -> HEADER_TYPE
            is CurrencyRateItem.Rate -> RATE_TYPE
            else -> error("Unsupported item type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position) ?: error("Currency rate item can't be null")) {
            is CurrencyRateItem.Header -> (holder as HeaderViewHolder).bind(item)
            is CurrencyRateItem.Rate -> (holder as RateViewHolder).bind(item)
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<CurrencyRateItem>() {
        override fun areItemsTheSame(
            oldItem: CurrencyRateItem,
            newItem: CurrencyRateItem
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: CurrencyRateItem,
            newItem: CurrencyRateItem
        ) = oldItem == newItem
    }
}

class HeaderViewHolder(
    private val binding: ItemHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(headerItem: CurrencyRateItem.Header) = with(binding) {
        val today = LocalDate.now()
        starIcon.isVisible = headerItem.date == today
        header.text = when (headerItem.date) {
            today -> root.context.getString(R.string.currency_rate_header_today)
            today.minusDays(1) -> root.context.getString(R.string.currency_rate_header_yesterday)
            else -> headerItem.date.toString()
        }
    }

    companion object {
        fun create(parent: ViewGroup): HeaderViewHolder {
            val binding = ItemHeaderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return HeaderViewHolder(binding)
        }
    }
}

class RateViewHolder(
    private val binding: ItemCurrencyRateBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(rateItem: CurrencyRateItem.Rate) = with(binding) {
        baseCurrencyHeader.text = root.context.getString(
            R.string.currency_rate_base_currency,
            rateItem.currencyRate.baseCurrencyCode
        )
        rate.text = root.context.getString(
            R.string.currency_rate_price,
            rateItem.currencyRate.rate.toString(),
            rateItem.currencyRate.currencyCode
        )
        currency.text = rateItem.currencyRate.currencyCode
        rootLayout.setOnClickListener { }
    }

    companion object {
        fun create(parent: ViewGroup): RateViewHolder {
            val binding = ItemCurrencyRateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return RateViewHolder(binding)
        }
    }
}