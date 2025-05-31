package com.example.urlshortenerandroid.presentation.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urlshortenerandroid.data.remote.dto.LinkStatistics
import com.example.urlshortenerandroid.domain.usecase.GetLinkStatsUC
import com.example.urlshortenerandroid.util.NetworkResult
import com.example.urlshortenerandroid.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the statistics screen.
 * Call refresh() for pull-to-refresh functionality.
 */
@HiltViewModel
class StatsVM @Inject constructor(
    savedState: SavedStateHandle,
    private val getStats: GetLinkStatsUC
) : ViewModel() {

    private val linkId: String = requireNotNull(savedState["id"])

    private val _uiState = MutableStateFlow<UiState<LinkStatistics>>(UiState.Loading)
    val uiState: StateFlow<UiState<LinkStatistics>> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() = viewModelScope.launch {
        _uiState.value = UiState.Loading
        when (val result = getStats(linkId)) {
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
}