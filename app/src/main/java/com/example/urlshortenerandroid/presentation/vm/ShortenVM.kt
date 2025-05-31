package com.example.urlshortenerandroid.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urlshortenerandroid.domain.usecase.CreateLinkUC
import com.example.urlshortenerandroid.data.remote.dto.LinkResponse
import com.example.urlshortenerandroid.util.NetworkResult
import com.example.urlshortenerandroid.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the "Shorten Link" screen.
 *
 * ― Holds UiState<LinkResponse>.
 * ― Calls UseCase to create a shortened link.
 */
@HiltViewModel
class ShortenVM @Inject constructor(
    private val createLink: CreateLinkUC
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<LinkResponse>>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun shorten(url: String) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        when (val result = createLink(url)) {
            is NetworkResult.Success -> {
                _uiState.value = UiState.Success(result.data)
            }
            is NetworkResult.Error -> {
                _uiState.value = UiState.Error(result.message)
            }
            else -> {
                _uiState.value = UiState.Error("Unknown error")
            }
        }
    }

    fun reset() {
        _uiState.value = UiState.Idle
    }
}
