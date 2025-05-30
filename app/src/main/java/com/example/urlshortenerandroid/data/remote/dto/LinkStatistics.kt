package com.example.urlshortenerandroid.data.remote.dto

import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class LinkStatistics(
    val shortCode: String,
    val clicks: Long,
    val uniqueVisitors: Long,
    val lastAccessedAt: LocalDateTime?,
    val isActive: Boolean
)