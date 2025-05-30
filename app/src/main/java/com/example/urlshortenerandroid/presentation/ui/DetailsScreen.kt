package com.example.urlshortenerandroid.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.urlshortenerandroid.data.remote.dto.LinkResponse
import com.example.urlshortenerandroid.presentation.vm.DeleteVM
import com.example.urlshortenerandroid.presentation.vm.DetailsVM
import com.example.urlshortenerandroid.util.UiState
import kotlinx.coroutines.flow.collectLatest

/**
 * Экран деталей ссылки.
 *
 * @param linkId передаётся как NavArg, но SavedStateHandle уже есть в VM.
 * @param onStatsClick навигация к экрану статистики.
 */
@Composable
fun DetailsScreen(
    linkId: String,                      // нужен только для ключа NavBackStack
    vm: DetailsVM = hiltViewModel(),
    deleteVM: DeleteVM = hiltViewModel(), // один VM на экран
    onStatsClick: () -> Unit
) {
    val state by vm.uiState.collectAsState()
    val ctx = LocalContext.current
    val clipboard = LocalClipboardManager.current
    var showDialog by remember { mutableStateOf(false) }

    // Слушаем одноразовые события DeleteVM
    LaunchedEffect(Unit) {
        deleteVM.events.collectLatest {
            when (it) {
                DeleteVM.Event.Success -> {
                    Toast.makeText(ctx, "Удалено", Toast.LENGTH_SHORT).show()
                    // Закрыть экран — пусть navController.handleBack() в вызывающем месте
                }
                is DeleteVM.Event.Error -> {
                    Toast.makeText(ctx, it.msg, Toast.LENGTH_LONG).show()
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
                    Text("Загружаем…")
                }
            }

            is UiState.Error -> Text(
                (state as UiState.Error).msg,
                color = MaterialTheme.colorScheme.error
            )

            is UiState.Success -> {
                val link = (state as UiState.Success<LinkResponse>).data
                /* --- Основная информация --- */
                Text("ID: ${link.shortCode}")
                Text("Исходный URL:")
                Text(link.originalUrl, style = MaterialTheme.typography.bodyMedium)

                Text("Короткий URL:")
                Text(link.url, color = MaterialTheme.colorScheme.primary)

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = {
                        clipboard.setText(AnnotatedString(link.url))
                        Toast.makeText(ctx, "Скопировано", Toast.LENGTH_SHORT).show()
                    },
                        modifier = Modifier.fillMaxWidth()
                        ) { Text("Копировать") }

                    Button(
                        onClick = onStatsClick,
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Статистика") }

                    OutlinedButton(
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        ),
                        onClick = { showDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Удалить") }
                }
            }

            else -> {}
        }
    }

    /* Диалог подтверждения удаления */
    if (showDialog) {
        DeleteDialog(
            onConfirm = { deleteVM.delete() },
            onDismiss = { showDialog = false }
        )
    }
}