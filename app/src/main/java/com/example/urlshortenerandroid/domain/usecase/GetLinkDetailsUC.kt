package com.example.urlshortenerandroid.domain.usecase

import com.example.urlshortenerandroid.data.remote.dto.LinkResponse
import com.example.urlshortenerandroid.data.repository.LinkRepositoryImpl
import javax.inject.Inject

/**
 * UC для получения детальной информации о ссылке.
 */
class GetLinkDetailsUC @Inject constructor(
    private val repo: LinkRepositoryImpl
) {
    suspend operator fun invoke(id: String): Result<LinkResponse> = repo.details(id)
}