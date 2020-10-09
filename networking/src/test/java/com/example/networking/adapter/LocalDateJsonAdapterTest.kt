package com.example.networking.adapter

import io.kotest.matchers.shouldBe
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
        subject.fromJson("2020-01-23") shouldBe LocalDate.of(2020, 1, 23)
        subject.fromJson("2019-12-28") shouldBe LocalDate.of(2019, 12, 28)
    }

    @Test
    fun `converts to json`() {
        subject.toJson(LocalDate.of(2020, 2, 3)) shouldBe "2020-02-03"
        subject.toJson(LocalDate.of(2019, 11, 11)) shouldBe "2019-11-11"
    }
}