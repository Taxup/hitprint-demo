package kz.app.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun GenericDialog(
    modifier: Modifier = Modifier,
    title: String? = null,
    description: String? = null,
    positive: String,
    positiveAction: (() -> Unit)? = null,
    negative: String? = null,
    negativeAction: (() -> Unit)? = null,
    onRemoveHeadFromQueue: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onRemoveHeadFromQueue()
        },
        title = {
            Text(
                text = title ?: stringResource(id = R.string.attention)
            )
        },
        text = {
            if (description != null) {
                Text(text = description)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    positiveAction?.invoke()
                    onRemoveHeadFromQueue.invoke()
                },
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color(0xFF4CAF50)
                )
            ) {
                Text(text = positive)
            }
        },
        dismissButton = {
            negative?.let {
                TextButton(
                    onClick = {
                        negativeAction?.invoke()
                        onRemoveHeadFromQueue.invoke()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = Color(0xFF4CAF50)
                    )
                ) {
                    Text(text = negative)
                }
            }
        }
    )
}