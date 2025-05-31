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
import com.example.urlshortenerandroid.presentation.ui.components.FullWidthButton
import com.example.urlshortenerandroid.presentation.ui.components.LoadingRow
import com.example.urlshortenerandroid.presentation.vm.StatsVM
import com.example.urlshortenerandroid.util.UiState

/**
 * Screen showing link statistics: total clicks, unique visitors, last access time.
 *
 * @param linkShortCode The short code of the link to fetch stats for.
 * @param vm StatsVM instance (Hilt-injected).
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
            TopAppBar(
                title = { Text(stringResource(R.string.title_statistics)) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                UiState.Loading -> {
                    LoadingRow(
                        text = stringResource(R.string.text_loading),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                is UiState.Error -> {
                    Text(
                        text = (state as UiState.Error).msg,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is UiState.Success -> {
                    val stats = (state as UiState.Success<LinkStatistics>).data
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
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

                        Spacer(modifier = Modifier.height(12.dp))

                        FullWidthButton(onClick = { vm.refresh() }) {
                            Text(stringResource(R.string.button_refresh))
                        }
                    }
                }

                else -> {
                    // UiState.Idle or other states – no-op
                }
            }
        }
    }
}
