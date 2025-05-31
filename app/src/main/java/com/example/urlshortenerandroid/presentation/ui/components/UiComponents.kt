package com.example.urlshortenerandroid.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A Row containing a CircularProgressIndicator, a Spacer, and a Text.
 * Used everywhere to show a "loading…" state with an indicator.
 *
 * @param text The label to display next to the spinner.
 * @param modifier Modifier to apply to the Row container.
 * @param indicatorSize The size of the CircularProgressIndicator.
 * @param spacerWidth The horizontal space between the indicator and the text.
 * @param textStyle Optional text style for the label.
 */
@Composable
fun LoadingRow(
    text: String,
    modifier: Modifier = Modifier,
    indicatorSize: Dp = 24.dp,
    spacerWidth: Dp = 8.dp,
    textStyle: androidx.compose.ui.text.TextStyle? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(indicatorSize)
        )
        Spacer(modifier = Modifier.width(spacerWidth))
        if (textStyle != null) {
            Text(text, style = textStyle)
        } else {
            Text(text)
        }
    }
}


/**
 * Full-width OutlinedButton.
 * Encapsulates `Modifier.fillMaxWidth()` and default outlined-button colors.
 * If you need to change the style later, update this in one place.
 *
 * @param onClick Callback invoked when the button is clicked.
 * @param enabled Whether the button is enabled.
 * @param content Composable content inside the button (usually a Text).
 */
@Composable
fun FullWidthOutlinedButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.outlinedButtonColors()
    ) {
        content()
    }
}


/**
 * Full-width primary Button.
 * Encapsulates `Modifier.fillMaxWidth()` and default button colors.
 *
 * @param onClick Callback invoked when the button is clicked.
 * @param enabled Whether the button is enabled.
 * @param content Composable content inside the button (usually a Text).
 */
@Composable
fun FullWidthButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth()
    ) {
        content()
    }
}

/**
 * Full-width outlined button styled for "danger" actions (e.g. Delete).
 * Uses `MaterialTheme.colorScheme.error` as the text color.
 *
 * @param onClick Callback invoked when the button is clicked.
 * @param enabled Whether the button is enabled.
 * @param content Composable content inside the button (usually a Text).
 */
@Composable
fun FullWidthDangerOutlinedButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.error
        )
    ) {
        content()
    }
}