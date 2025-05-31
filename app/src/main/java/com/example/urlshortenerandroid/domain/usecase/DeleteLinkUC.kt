package com.example.urlshortenerandroid.domain.usecase

import com.example.urlshortenerandroid.data.repository.LinkRepository
import javax.inject.Inject
import retrofit2.Response

/**
 * Use case to delete a short link.
 *
 * API returns 204 No Content → Result<Unit>.
 */
class DeleteLinkUC @Inject constructor(
    private val repo: LinkRepository
) {
    suspend operator fun invoke(id: String): Result<Response<Unit>> = repo.delete(id)
}
