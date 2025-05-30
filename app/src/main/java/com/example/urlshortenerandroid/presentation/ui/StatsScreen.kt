package com.example.urlshortenerandroid.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.urlshortenerandroid.data.remote.dto.LinkStatistics
import com.example.urlshortenerandroid.presentation.vm.StatsVM
import com.example.urlshortenerandroid.util.UiState

/**
 * Экран статистики: клики и дата создания.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    linkId: String,
    vm: StatsVM = hiltViewModel()
) {
    val state by vm.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Статистика") }) }
    ) { inner ->
        Box(Modifier.padding(inner).fillMaxSize(), contentAlignment = Alignment.Center) {
            when (state) {
                UiState.Loading -> CircularProgressIndicator()

                is UiState.Error -> Text(
                    (state as UiState.Error).msg,
                    color = MaterialTheme.colorScheme.error
                )

                is UiState.Success -> {
                    val stats = (state as UiState.Success<LinkStatistics>).data
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Всего кликов: ${stats.clicks}", style = MaterialTheme.typography.headlineSmall)
                        Text("Уникальных посетителей: ${stats.uniqueVisitors}", style = MaterialTheme.typography.bodyMedium)
                        Text("Последний переход: ${stats.lastAccessedAt}", style = MaterialTheme.typography.bodyMedium)
                        Button(onClick = { vm.refresh() }) { Text("Обновить") }
                    }
                }

                else -> {}
            }
        }
    }
}