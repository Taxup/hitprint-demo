package kz.app.components.address

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kz.app.components.R

@ExperimentalComposeUiApi
@Composable
fun AddressEditField(address: String, onChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        value = address,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor = Color(0xFF19924F),
            unfocusedIndicatorColor = Color.LightGray
        ),
        label = {
            Text(text = stringResource(id = R.string.enter_address))
        },
        onValueChange = onChange,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        )
    )
}