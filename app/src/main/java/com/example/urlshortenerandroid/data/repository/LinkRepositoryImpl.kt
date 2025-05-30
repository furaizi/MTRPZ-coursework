package com.example.urlshortenerandroid.data.repository

import com.example.urlshortenerandroid.data.remote.LinkApi
import com.example.urlshortenerandroid.data.remote.dto.CreateLinkRequest
import com.example.urlshortenerandroid.data.remote.dto.LinkResponse
import com.example.urlshortenerandroid.data.remote.dto.LinkStatistics
import javax.inject.Inject

class LinkRepositoryImpl @Inject constructor(private val api: LinkApi)
    : LinkRepository {

    override suspend fun create(url: String): Result<LinkResponse> = runCatching {
        api.create(CreateLinkRequest(url))
    }

    override suspend fun details(id: String): Result<LinkResponse> = runCatching {
        api.details(id)
    }

    override suspend fun stats(id: String): Result<LinkStatistics> = runCatching {
        api.stats(id)
    }

    override suspend fun delete(id: String) = runCatching {
        api.delete(id)
    }
}