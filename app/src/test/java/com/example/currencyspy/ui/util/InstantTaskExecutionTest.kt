package com.example.currencyspy.ui.util

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

abstract class InstantTaskExecutionTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var testCoroutineDispatcher: TestCoroutineDispatcher

    @Before
    @CallSuper
    open fun setup() {
        testCoroutineDispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(testCoroutineDispatcher)
    }
}
