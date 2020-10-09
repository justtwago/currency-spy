package com.example.networking.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import javax.inject.Inject

internal class LocalDateJsonAdapter @Inject constructor() {
    @ToJson
    fun toJson(localDate: LocalDate): String = localDate.toString()

    @FromJson
    fun fromJson(json: String): LocalDate = LocalDate.parse(json)
}
