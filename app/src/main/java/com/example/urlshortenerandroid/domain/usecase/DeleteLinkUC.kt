package com.example.urlshortenerandroid.domain.usecase

import com.example.urlshortenerandroid.data.repository.LinkRepository
import javax.inject.Inject
import retrofit2.Response

/**
 * UC удаляет короткую ссылку.
 *
 * API возвращает 204 No Content → Result<Unit>.
 */
class DeleteLinkUC @Inject constructor(
    private val repo: LinkRepository
) {
    suspend operator fun invoke(id: String): Result<Response<Unit>> = repo.delete(id)
}