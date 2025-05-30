package com.example.urlshortenerandroid.data.remote.dto

import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class LinkResponse(
    val shortCode: String,
    val url: String,
    val originalUrl: String,
    val expiresAt: LocalDateTime?,
    val createdAt: LocalDateTime
)