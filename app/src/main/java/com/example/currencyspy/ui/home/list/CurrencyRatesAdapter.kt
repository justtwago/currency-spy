package com.example.currencyspy.ui.home.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyspy.databinding.ItemCurrencyRateBinding
import com.example.domain.CurrencyRate

class CurrencyRatesAdapter : PagingDataAdapter<CurrencyRate, ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCurrencyRateBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: error("Currency rate item can't be null")
        holder.bind(item)
    }

    private object DiffCallback : DiffUtil.ItemCallback<CurrencyRate>() {
        override fun areItemsTheSame(
            oldItem: CurrencyRate,
            newItem: CurrencyRate
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: CurrencyRate,
            newItem: CurrencyRate
        ) = oldItem.date == newItem.date && oldItem.currencyCode == newItem.currencyCode
    }
}

class ViewHolder(
    private val binding: ItemCurrencyRateBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(currencyRate: CurrencyRate) {
        binding.currencyRate.text = "${currencyRate.rate} ${currencyRate.currencyCode}"
    }
}