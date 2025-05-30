package com.example.urlshortenerandroid.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateLinkRequest(val url: String)