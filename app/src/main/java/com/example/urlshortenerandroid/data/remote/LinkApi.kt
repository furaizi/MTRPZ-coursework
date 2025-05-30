package com.example.urlshortenerandroid.data.remote

import com.example.urlshortenerandroid.data.remote.dto.CreateLinkRequest
import com.example.urlshortenerandroid.data.remote.dto.LinkResponse
import com.example.urlshortenerandroid.data.remote.dto.LinkStatistics
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LinkApi {
    @POST("links")
    suspend fun create(@Body request: CreateLinkRequest): LinkResponse

    @GET("links/{shortCode}")
    suspend fun details(@Path("shortCode") shortCode: String): LinkResponse

    @GET("links/{shortCode}/stats")
    suspend fun stats(@Path("shortCode") shortCode: String): LinkStatistics

    @DELETE("links/{shortCode}")
    suspend fun delete(@Path("shortCode") shortCode: String): Response<Unit>
}