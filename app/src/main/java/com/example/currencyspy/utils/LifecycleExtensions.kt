package com.example.currencyspy.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class NonNullObserver<V>(val block: (V) -> Unit) : Observer<V> {
    override fun onChanged(t: V?) {
        t?.let { block(t) }
    }
}

fun <T> LiveData<T>.observeNotNull(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, NonNullObserver(observer))
}
