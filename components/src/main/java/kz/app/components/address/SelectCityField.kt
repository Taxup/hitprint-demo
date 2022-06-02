package kz.app.components.address

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kz.app.components.R

@Composable
fun SelectCityField(city: String, onUpdateCity: (String) -> Unit) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            Icon(
                imageVector = Icons.Rounded.ArrowDropDown,
                contentDescription = null,
                Modifier.size(24.dp)
            )
        },
        shape = RoundedCornerShape(8.dp),
        value = city,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.LightGray,
            unfocusedIndicatorColor = Color.LightGray
        ),
        label = {
            Text(text = stringResource(id = R.string.select_city))
        },
        onValueChange = {             
            onUpdateCity.invoke(it)
        },
        readOnly = true
    )
}