package com.example.urlshortenerandroid.domain.usecase

import com.example.urlshortenerandroid.data.remote.dto.LinkResponse
import com.example.urlshortenerandroid.data.repository.LinkRepository
import javax.inject.Inject

/**
 * Use case to fetch detailed information about a link.
 */
class GetLinkDetailsUC @Inject constructor(
    private val repo: LinkRepository
) {
    suspend operator fun invoke(id: String) = repo.details(id)
}
