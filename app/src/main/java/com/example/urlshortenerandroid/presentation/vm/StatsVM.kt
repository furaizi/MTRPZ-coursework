package com.example.urlshortenerandroid.presentation.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urlshortenerandroid.data.remote.dto.LinkStatistics
import com.example.urlshortenerandroid.domain.usecase.GetLinkStatsUC
import com.example.urlshortenerandroid.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * VM для экрана статистики переходов.
 * Можно дергать refresh() для pull-to-refresh.
 */
@HiltViewModel
class StatsVM @Inject constructor(
    savedState: SavedStateHandle,
    private val getStats: GetLinkStatsUC
) : ViewModel() {

    private val linkId: String = requireNotNull(savedState["id"])

    private val _uiState = MutableStateFlow<UiState<LinkStatistics>>(UiState.Loading)
    val uiState: StateFlow<UiState<LinkStatistics>> = _uiState.asStateFlow()

    init { refresh() }

    fun refresh() = viewModelScope.launch {
        _uiState.value = UiState.Loading
        getStats(linkId).fold(
            onSuccess = { _uiState.value = UiState.Success(it) },
            onFailure = { _uiState.value = UiState.Error(it.message ?: "Не удалось получить статистику") }
        )
    }
}