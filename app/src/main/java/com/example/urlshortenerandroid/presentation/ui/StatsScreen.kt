package com.example.urlshortenerandroid.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.urlshortenerandroid.R
import com.example.urlshortenerandroid.data.remote.dto.LinkStatistics
import com.example.urlshortenerandroid.presentation.vm.StatsVM
import com.example.urlshortenerandroid.util.UiState

/**
 * Screen showing link statistics: total clicks and last access time.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    linkShortCode: String,
    vm: StatsVM = hiltViewModel()
) {
    val state by vm.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.title_statistics)) })
        }
    ) { inner ->
        Box(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                UiState.Loading -> CircularProgressIndicator()

                is UiState.Error -> Text(
                    (state as UiState.Error).msg,
                    color = MaterialTheme.colorScheme.error
                )

                is UiState.Success -> {
                    val stats = (state as UiState.Success<LinkStatistics>).data
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            stringResource(R.string.text_total_clicks, stats.clicks),
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            stringResource(R.string.text_unique_visitors, stats.uniqueVisitors),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            stringResource(R.string.text_last_access, stats.lastAccessedAt.toString()),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Button(onClick = { vm.refresh() }) {
                            Text(stringResource(R.string.button_refresh))
                        }
                    }
                }

                else -> { /* no-op */ }
            }
        }
    }
}
