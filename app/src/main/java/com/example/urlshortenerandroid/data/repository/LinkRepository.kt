package com.example.urlshortenerandroid.data.repository

import com.example.urlshortenerandroid.data.remote.dto.LinkResponse
import com.example.urlshortenerandroid.data.remote.dto.LinkStatistics
import com.example.urlshortenerandroid.util.NetworkResult
import retrofit2.Response

interface LinkRepository {
    suspend fun create(url: String): NetworkResult<LinkResponse>
    suspend fun details(id: String): NetworkResult<LinkResponse>
    suspend fun stats(id: String): NetworkResult<LinkStatistics>
    suspend fun delete(id: String): NetworkResult<Response<Unit>>
}