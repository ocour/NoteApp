package com.example.noteapp.ui.components

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun NoteDeletionDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    content: String,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = "Confirm")
            }
        },
        icon = {
            Icon(imageVector = Icons.Outlined.Warning, contentDescription = null)
        },
        title = { Text(text = title) },
        text = {
            Text(
                text = content,
                overflow = TextOverflow.Ellipsis,
                maxLines = 4
            )
        }
    )
}