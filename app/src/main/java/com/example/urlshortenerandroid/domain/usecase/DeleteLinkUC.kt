package com.example.urlshortenerandroid.domain.usecase

import com.example.urlshortenerandroid.data.repository.LinkRepositoryImpl
import javax.inject.Inject
import retrofit2.Response

/**
 * UC удаляет короткую ссылку.
 *
 * API возвращает 204 No Content → Result<Unit>.
 */
class DeleteLinkUC @Inject constructor(
    private val repo: LinkRepositoryImpl
) {
    suspend operator fun invoke(id: String): Result<Response<Unit>> = repo.delete(id)
}