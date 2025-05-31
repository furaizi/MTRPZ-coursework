package com.example.urlshortenerandroid.data.repository

import com.example.urlshortenerandroid.data.remote.LinkApi
import com.example.urlshortenerandroid.data.remote.dto.CreateLinkRequest
import com.example.urlshortenerandroid.data.remote.dto.LinkResponse
import com.example.urlshortenerandroid.data.remote.dto.LinkStatistics
import com.example.urlshortenerandroid.util.NetworkResult
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class LinkRepositoryImpl @Inject constructor(
    private val api: LinkApi
) : LinkRepository {

    override suspend fun create(url: String): NetworkResult<LinkResponse> {
        return try {
            val response = api.create(CreateLinkRequest(url))
            NetworkResult.Success(response)
        } catch (e: HttpException) {
            val code = e.code()
            val message = "HTTP $code: ${e.response()?.errorBody()?.string() ?: e.message()}"
            NetworkResult.Error(message, code)
        } catch (e: IOException) {
            NetworkResult.Error("Network error: ${e.localizedMessage}")
        } catch (e: Exception) {
            NetworkResult.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun details(id: String): NetworkResult<LinkResponse> {
        return try {
            val response = api.details(id)
            NetworkResult.Success(response)
        } catch (e: HttpException) {
            val code = e.code()
            val message = when (code) {
                404 -> "404: Link $id not found"
                else -> "HTTP $code: ${e.response()?.errorBody()?.string() ?: e.message()}"
            }
            NetworkResult.Error(message, code)
        } catch (e: IOException) {
            NetworkResult.Error("Network error: ${e.localizedMessage}")
        } catch (e: Exception) {
            NetworkResult.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun stats(id: String): NetworkResult<LinkStatistics> {
        return try {
            val response = api.stats(id)
            NetworkResult.Success(response)
        } catch (e: HttpException) {
            val code = e.code()
            val message = when (code) {
                404 -> "404: Statistics for link $id not found"
                else -> "HTTP $code: ${e.response()?.errorBody()?.string() ?: e.message()}"
            }
            NetworkResult.Error(message, code)
        } catch (e: IOException) {
            NetworkResult.Error("Network error: ${e.localizedMessage}")
        } catch (e: Exception) {
            NetworkResult.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun delete(id: String): NetworkResult<Response<Unit>> {
        return try {
            val response = api.delete(id)
            if (response.isSuccessful) {
                NetworkResult.Success(response)
            } else {
                val code = response.code()
                val message = "HTTP $code: ${response.errorBody()?.string() ?: "Error deleting"}"
                NetworkResult.Error(message, code)
            }
        } catch (e: HttpException) {
            val code = e.code()
            val message = "HTTP $code: ${e.response()?.errorBody()?.string() ?: e.message()}"
            NetworkResult.Error(message, code)
        } catch (e: IOException) {
            NetworkResult.Error("Network error: ${e.localizedMessage}")
        } catch (e: Exception) {
            NetworkResult.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}