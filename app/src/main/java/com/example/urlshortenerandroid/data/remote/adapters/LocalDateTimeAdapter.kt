package com.example.urlshortenerandroid.data.remote.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Custom JsonAdapter for java.time.LocalDateTime.
 * Assumes ISO-8601 format without zone, e.g. "2025-05-31T12:34:56".
 */
class LocalDateTimeAdapter {
    @ToJson
    fun toJson(value: LocalDateTime): String =
        value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    @FromJson
    fun fromJson(value: String): LocalDateTime =
        LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}