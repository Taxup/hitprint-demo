package kz.app.ui_upload_documents.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.app.hitprint_domain.Document
import kz.app.ui_upload_documents.extension.toMB

@ExperimentalComposeUiApi
@Composable
fun DocumentItem(
    document: Document,
    onComment: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = document.filename,
            fontWeight = FontWeight(200),
            fontSize = 14.sp,
            color = Color(0xFF344054),
            fontStyle = FontStyle.Italic
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Size - ${document.size.toMB()}MB",
            fontSize = 12.sp,
            color = Color(0xFF667085)
        )

        if (document.error.isNotEmpty()) {
            Column {
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = document.error,
                    fontSize = 12.sp,
                    color = Color(0xFFF04438)
                )
            }
        }

        if (document.progress == 1f && document.uuid != null) {
            Column {
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Successful",
                    fontSize = 12.sp,
                    color = Color(0xFF12B76A)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        if (document.uuid == null && document.progress == 1f) {
            LinearProgressIndicator(
                color = Color(0xFF19924F),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(50))
            )
        } else {
            LinearProgressIndicator(
                progress = document.progress,
                color = Color(0xFF19924F),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(50))
            )
        }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            value = document.comment,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color(0xFF19924F),
                unfocusedIndicatorColor = Color.LightGray
            ),
            placeholder = {
                Text(text = "/* leave a comment */")
            },
            onValueChange = {
                onComment.invoke(it)
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            })
        )
    }
}