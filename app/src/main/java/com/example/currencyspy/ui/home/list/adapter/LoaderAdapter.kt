package com.example.currencyspy.ui.home.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyspy.databinding.ItemLoaderBinding

class LoaderAdapter(
    private val doOnRetryClicked: () -> Unit
) : LoadStateAdapter<LoaderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val binding = ItemLoaderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LoaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState, doOnRetryClicked)
    }

}

class LoaderViewHolder(
    private val binding: ItemLoaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState, doOnRetryClicked: () -> Unit) = with(binding) {
        progressLoader.isVisible = loadState is LoadState.Loading
        progressTitle.isVisible = loadState is LoadState.Loading
        errorMessage.isVisible = loadState is LoadState.Error
        errorMessage.setOnClickListener { doOnRetryClicked() }
    }
}