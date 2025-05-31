package com.example.urlshortenerandroid.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.urlshortenerandroid.R
import com.example.urlshortenerandroid.data.remote.dto.LinkResponse
import com.example.urlshortenerandroid.presentation.ui.components.*
import com.example.urlshortenerandroid.presentation.vm.ShortenVM
import com.example.urlshortenerandroid.util.UiState

/**
 * Screen "Shorten Link".
 *
 * @param vm ShortenVM instance (Hilt-injected).
 * @param onCreated Callback with the new linkId to navigate to DetailsScreen.
 */
@Composable
fun ShortenScreen(
    vm: ShortenVM = hiltViewModel(),
    onCreated: (String) -> Unit
) {
    val state by vm.uiState.collectAsState()
    var url by remember { mutableStateOf("") }
    val clipboard: ClipboardManager = LocalClipboardManager.current
    val ctx = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text(stringResource(R.string.label_long_url)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FullWidthButton(
                onClick = { vm.shorten(url) },
                enabled = url.isNotBlank()
            ) {
                Text(stringResource(R.string.button_shorten))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        when (state) {
            UiState.Idle -> {
                // No UI for idle state
            }

            UiState.Loading -> {
                LoadingRow(
                    text = stringResource(R.string.text_creating_link),
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
                val link = (state as UiState.Success<LinkResponse>).data

                Text(
                    stringResource(R.string.label_short_url),
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    link.url,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FullWidthOutlinedButton(onClick = {
                        clipboard.setText(AnnotatedString(link.url))
                        Toast.makeText(ctx, ctx.getString(R.string.text_copied), Toast.LENGTH_SHORT).show()
                    }) {
                        Text(stringResource(R.string.button_copy))
                    }

                    FullWidthButton(onClick = { onCreated(link.shortCode) }) {
                        Text(stringResource(R.string.button_details))
                    }
                }
            }
        }
    }
}
