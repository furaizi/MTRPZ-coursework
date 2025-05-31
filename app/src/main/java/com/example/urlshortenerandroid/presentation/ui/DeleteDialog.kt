package com.example.urlshortenerandroid.presentation.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.urlshortenerandroid.R

/**
 * Universal confirmation dialog for deletion.
 *
 * Renders on top of current content when showDialog == true.
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
                onConfirm()
                onDismiss()
            }) {
                Text(stringResource(R.string.button_delete))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.button_cancel))
            }
        },
        title = { Text(stringResource(R.string.dialog_delete_title)) },
        text = { Text(stringResource(R.string.dialog_delete_text)) }
    )
}
