package com.example.urlshortenerandroid.domain.usecase

import com.example.urlshortenerandroid.data.remote.dto.LinkResponse
import com.example.urlshortenerandroid.data.repository.LinkRepository
import javax.inject.Inject

/**
 * Use case to create a short link.
 *
 * @param url the full (long) URL to shorten.
 * @return Result<LinkResponse> — success with LinkResponse on HTTP 200 OK or failure on network/validation error.
 */
class CreateLinkUC @Inject constructor(
    private val repo: LinkRepository
) {
    suspend operator fun invoke(url: String): Result<LinkResponse> = repo.create(url)
}