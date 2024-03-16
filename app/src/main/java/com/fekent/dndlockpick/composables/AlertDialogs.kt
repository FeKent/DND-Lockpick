package com.fekent.dndlockpick.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun AlertDialogs(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    message: String
) {
    AlertDialog(onDismissRequest = onDismiss, confirmButton = {
        TextButton(onClick = onConfirm) {
            Text(text = "Ok")
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text(text = "Cancel")
        }
    }, text = { Text(text = message)})
}