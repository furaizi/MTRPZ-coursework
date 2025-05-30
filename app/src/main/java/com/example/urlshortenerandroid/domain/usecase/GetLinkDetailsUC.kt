package com.example.urlshortenerandroid.domain.usecase

import com.example.urlshortenerandroid.data.remote.dto.LinkResponse
import com.example.urlshortenerandroid.data.repository.LinkRepository
import jakarta.inject.Inject

/**
 * UC для получения детальной информации о ссылке.
 */
class GetLinkDetailsUC @Inject constructor(
    private val repo: LinkRepository
) {
    suspend operator fun invoke(id: String): Result<LinkResponse> = repo.details(id)
}