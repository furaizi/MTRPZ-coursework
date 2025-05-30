package com.example.urlshortenerandroid.domain.usecase

import com.example.urlshortenerandroid.data.remote.dto.LinkResponse
import com.example.urlshortenerandroid.data.repository.LinkRepositoryImpl
import javax.inject.Inject

/**
 * UC для создания короткой ссылки.
 *
 * @param url полный (длинный) URL, который надо сократить.
 * @return Result<Link> — success c объектом Link при 200 OK или failure при ошибке сети/валидации.
 */
class CreateLinkUC @Inject constructor(
    private val repo: LinkRepositoryImpl
) {
    suspend operator fun invoke(url: String): Result<LinkResponse> = repo.create(url)
}