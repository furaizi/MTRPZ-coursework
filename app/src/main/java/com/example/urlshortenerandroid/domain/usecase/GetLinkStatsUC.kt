package com.example.urlshortenerandroid.domain.usecase

import com.example.urlshortenerandroid.data.remote.dto.LinkStatistics
import com.example.urlshortenerandroid.data.repository.LinkRepository
import jakarta.inject.Inject

/**
 * UC для получения базовой статистики переходов по ссылке.
 */
class GetLinkStatsUC @Inject constructor(
    private val repo: LinkRepository
) {
    suspend operator fun invoke(id: String): Result<LinkStatistics> = repo.stats(id)
}