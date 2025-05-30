package com.example.urlshortenerandroid.data.repository

import com.example.urlshortenerandroid.data.remote.dto.LinkResponse
import com.example.urlshortenerandroid.data.remote.dto.LinkStatistics
import retrofit2.Response

interface LinkRepository {
    suspend fun create(url: String): Result<LinkResponse>
    suspend fun details(id: String): Result<LinkResponse>
    suspend fun stats(id: String): Result<LinkStatistics>
    suspend fun delete(id: String): Result<Response<Unit>>
}