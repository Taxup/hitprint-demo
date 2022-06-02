package kz.app.ui_otp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun OtpView(
    length: Int = 6,
    code: String,
    readOnly: Boolean,
    onCodeChange: (String) -> Unit,
    onFilled: (code: String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    TextField(
        value = code,
        readOnly = readOnly,
        onValueChange = {
            if (it.length <= length) {
                onCodeChange(it)
            }
            if (it.length == 6) {
                onFilled.invoke(it)
            }
        },
        modifier = Modifier
            .size(0.dp)
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        )
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        (0 until length).map { index ->
            OtpCell(
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        focusRequester.requestFocus()
                        keyboard?.show()
                    }
                    .clip(shape = RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0xFF5DB075), shape = RoundedCornerShape(12.dp)),
                value = code.getOrNull(index)?.toString() ?: "",
                isCursorVisible = code.length == index
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
    }

}

@Composable
fun OtpCell(
    modifier: Modifier,
    value: String,
    isCursorVisible: Boolean = false
) {
    val scope = rememberCoroutineScope()
    val (cursorSymbol, setCursorSymbol) = remember { mutableStateOf("") }

    LaunchedEffect(key1 = cursorSymbol,isCursorVisible) {
        if (isCursorVisible){
            scope.launch {
                delay(350)
                setCursorSymbol(if (cursorSymbol.isEmpty()) "|" else "")
            }
        }
    }

    Box(
        modifier = modifier
    ) {

        Text(
            text = if (isCursorVisible) cursorSymbol else value,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

