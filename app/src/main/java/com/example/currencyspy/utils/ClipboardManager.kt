package com.example.currencyspy.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ClipboardManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val clipboardManager
        get() = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    fun copyText(text: String) {
        clipboardManager.setPrimaryClip(ClipData.newPlainText("CurrencyRate", text))
    }
}
