package com.example.urlshortener.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urlshortener.domain.model.Link
import com.example.urlshortener.domain.usecase.CreateLinkUC
import com.example.urlshortener.util.UiState
import com.example.urlshortenerandroid.data.remote.dto.LinkResponse
import com.example.urlshortenerandroid.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * VM для экрана "Сократить ссылку".
 *
 * ― Хранит UiState<Link>.
 * ― Вызывает UseCase для создания ссылки.
 */
@HiltViewModel
class ShortenVM @Inject constructor(
    private val createLink: CreateLinkUC
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<LinkResponse>>(UiState.Success(null))
    val uiState = _uiState.asStateFlow()

    fun shorten(url: String) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        createLink(url).fold(
            onSuccess = { link -> _uiState.value = UiState.Success(link) },
            onFailure = { e   -> _uiState.value = UiState.Error(e.localizedMessage ?: "Неизвестная ошибка") }
        )
    }

    fun reset() { _uiState.value = UiState.Success(null) }
}
