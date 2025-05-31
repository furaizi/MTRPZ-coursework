package com.example.urlshortenerandroid.domain.usecase

import com.example.urlshortenerandroid.data.remote.dto.LinkStatistics
import com.example.urlshortenerandroid.data.repository.LinkRepository
import javax.inject.Inject

/**
 * Use case to fetch basic click statistics for a link.
 */
class GetLinkStatsUC @Inject constructor(
    private val repo: LinkRepository
) {
    suspend operator fun invoke(id: String) = repo.stats(id)
}
