package com.example.urlshortenerandroid.presentation.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urlshortener.domain.model.Link
import com.example.urlshortener.domain.usecase.GetLinkDetailsUC
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
 * VM экрана с детальной информацией о ссылке.
 * linkId приходит из NavArgs в SavedStateHandle: navController.navigate("Details/{id}").
 */
@HiltViewModel
class DetailsVM @Inject constructor(
    savedState: SavedStateHandle,
    private val getDetails: GetLinkDetailsUC
) : ViewModel() {

    private val linkId: String = requireNotNull(savedState["id"]) { "linkId missing" }

    private val _uiState = MutableStateFlow<UiState<LinkResponse>>(UiState.Loading)
    val uiState: StateFlow<UiState<LinkResponse>> = _uiState.asStateFlow()

    init { load() }

    fun load() = viewModelScope.launch {
        _uiState.value = UiState.Loading
        getDetails(linkId).fold(
            onSuccess = { _uiState.value = UiState.Success(it) },
            onFailure = { _uiState.value = UiState.Error(it.message ?: "Ошибка получения данных") }
        )
    }
}