package com.example.urlshortenerandroid.presentation.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

/**
 * Универсальный диалог подтверждения удаления.
 *
 * Отрисовывается поверх текущего контента, когда showDialog == true.
 */
@Composable
fun DeleteDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onConfirm(); onDismiss()
            }) { Text("Удалить") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Отмена") } },
        title = { Text("Удаление ссылки") },
        text = { Text("Вы уверены, что хотите удалить эту ссылку? Это действие необратимо.") }
    )
}