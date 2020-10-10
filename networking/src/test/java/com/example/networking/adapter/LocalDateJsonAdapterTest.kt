package com.example.networking.adapter

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class LocalDateTimeJsonAdapterTest {
    private lateinit var subject: LocalDateJsonAdapter

    @Before
    fun setUp() {
        subject = LocalDateJsonAdapter()
    }

    @Test
    fun `converts from json`() {
        assertEquals(LocalDate.of(2020, 1, 23), subject.fromJson("2020-01-23"))
        assertEquals(LocalDate.of(2019, 12, 28), subject.fromJson("2019-12-28"))
    }

    @Test
    fun `converts to json`() {
        assertEquals("2020-02-03", subject.toJson(LocalDate.of(2020, 2, 3)))
        assertEquals("2019-11-11", subject.toJson(LocalDate.of(2019, 11, 11)))
    }
}