package com.example.urlshortenerandroid.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.urlshortenerandroid.R
import com.example.urlshortenerandroid.data.remote.dto.LinkResponse
import com.example.urlshortenerandroid.presentation.vm.DeleteVM
import com.example.urlshortenerandroid.presentation.vm.DetailsVM
import com.example.urlshortenerandroid.util.UiState
import kotlinx.coroutines.flow.collectLatest

/**
 * Screen showing link details.
 *
 * @param linkId passed as NavArg; SavedStateHandle already holds this in the ViewModel.
 * @param onStatsClick navigation callback to the statistics screen.
 */
@Composable
fun DetailsScreen(
    linkId: String,                       // used only as a NavBackStack key
    vm: DetailsVM = hiltViewModel(),
    deleteVM: DeleteVM = hiltViewModel(), // one ViewModel per screen
    onStatsClick: () -> Unit,
    onDeleted: () -> Unit               // <-- NEW callback
) {
    val state by vm.uiState.collectAsState()
    val ctx = LocalContext.current
    val clipboard = LocalClipboardManager.current
    var showDialog by remember { mutableStateOf(false) }

    // Listen to one-time events from DeleteVM
    LaunchedEffect(Unit) {
        deleteVM.events.collectLatest { event ->
            when (event) {
                DeleteVM.Event.Success -> {
                    // 1) Show a toast
                    Toast.makeText(ctx, ctx.getString(R.string.toast_deleted), Toast.LENGTH_SHORT).show()
                    // 2) Invoke the callback so NavController pops back
                    onDeleted()
                }
                is DeleteVM.Event.Error -> {
                    Toast.makeText(ctx, event.msg, Toast.LENGTH_LONG).show()
                    showDialog = false
                }
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        when (state) {
            UiState.Loading -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator()
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.text_loading))
                }
            }

            is UiState.Error -> Text(
                text = (state as UiState.Error).msg,
                color = MaterialTheme.colorScheme.error
            )

            is UiState.Success -> {
                val link = (state as UiState.Success<LinkResponse>).data
                // --- Main information ---
                Text(
                    text = stringResource(R.string.label_id, link.shortCode),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(stringResource(R.string.label_original_url))
                Text(link.originalUrl, style = MaterialTheme.typography.bodyMedium)

                Text(stringResource(R.string.label_short_url_details))
                Text(link.url, color = MaterialTheme.colorScheme.primary)

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = {
                            clipboard.setText(AnnotatedString(link.url))
                            Toast.makeText(ctx, ctx.getString(R.string.text_copied), Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.button_copy))
                    }

                    Button(
                        onClick = onStatsClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.title_statistics))
                    }

                    OutlinedButton(
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        ),
                        onClick = { showDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.button_delete))
                    }
                }
            }

            else -> { /* no-op */ }
        }
    }

    // Confirmation dialog for deletion
    if (showDialog) {
        DeleteDialog(
            onConfirm = {
                deleteVM.delete()
            },
            onDismiss = { showDialog = false }
        )
    }
}
