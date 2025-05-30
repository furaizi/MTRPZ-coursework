package com.example.urlshortenerandroid.data.repository

import com.example.urlshortenerandroid.data.remote.LinkApi
import com.example.urlshortenerandroid.data.remote.dto.CreateLinkRequest
import com.example.urlshortenerandroid.data.remote.dto.LinkResponse
import com.example.urlshortenerandroid.data.remote.dto.LinkStatistics
import jakarta.inject.Inject

class LinkRepository @Inject constructor(private val api: LinkApi) {

    suspend fun create(url: String): Result<LinkResponse> = runCatching {
        api.create(CreateLinkRequest(url))
    }

    suspend fun details(id: String): Result<LinkResponse> = runCatching {
        api.details(id)
    }

    suspend fun stats(id: String): Result<LinkStatistics> = runCatching {
        api.stats(id)
    }

    suspend fun delete(id: String) = runCatching {
        api.delete(id)
    }
}