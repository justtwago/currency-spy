package com.example.currencyspy.utils

import android.content.ClipData
import android.content.Context
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class ClipboardManagerTest {
    private lateinit var subject: ClipboardManager

    @RelaxedMockK private lateinit var mockedContext: Context
    @RelaxedMockK private lateinit var mockedClipBoardManager: android.content.ClipboardManager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        subject = ClipboardManager(
            context = mockedContext
        )
    }

    @Test
    fun `sets primary clip when copying text to clipboard`() {
        every { mockedContext.getSystemService(Context.CLIPBOARD_SERVICE) } returns mockedClipBoardManager

        subject.copyText("3.2359823")

        verify {
            mockedClipBoardManager.setPrimaryClip(
                ClipData.newPlainText(
                    "CurrencyRate",
                    "3.2359823"
                )
            )
        }
    }
}