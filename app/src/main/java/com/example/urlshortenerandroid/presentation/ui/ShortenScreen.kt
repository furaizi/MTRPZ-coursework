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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.urlshortenerandroid.presentation.vm.ShortenVM
import com.example.urlshortenerandroid.data.remote.dto.LinkResponse
import com.example.urlshortenerandroid.util.UiState

/**
 * Экран «Сократить ссылку».
 *
 * @param onCreated callback с linkId — для навигации к DetailsScreen.
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
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Длинный URL") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = { vm.shorten(url) },
            enabled = url.isNotBlank(),
            modifier = Modifier.align(Alignment.End)
        ) { Text("Сократить") }

        when (state) {
            UiState.Idle -> { /* пусто */ }

            UiState.Loading -> Row {
                CircularProgressIndicator()
                Spacer(Modifier.width(8.dp))
                Text("Создаём ссылку…")
            }

            is UiState.Error -> {
                Text(
                    (state as UiState.Error).msg,
                    color = MaterialTheme.colorScheme.error
                )
            }

            is UiState.Success -> {
                val link = (state as UiState.Success<LinkResponse>).data
                Text("Короткая ссылка:", style = MaterialTheme.typography.labelLarge)
                Text(link.url, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = {
                        clipboard.setText(AnnotatedString(link.url))
                        Toast.makeText(ctx, "Скопировано", Toast.LENGTH_SHORT).show()
                    }) { Text("Копировать") }

                    Button(onClick = { onCreated(link.shortCode) }) { Text("Детали") }
                }
            }
        }
    }
}
