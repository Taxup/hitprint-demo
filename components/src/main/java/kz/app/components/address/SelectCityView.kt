package kz.app.components.address

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.dp

@Composable
fun SelectCityView(
    modifier: Modifier = Modifier,
    selectableCities: List<String>,
    onCitySelected: (city: String) -> Unit
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            modifier = Modifier
                .size(60.dp, 6.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(3.dp)),
            painter = ColorPainter(Color(0xFFBDBDBD)),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(selectableCities) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCitySelected.invoke(it) }) {
                    Text(modifier = Modifier.padding(16.dp), text = it)
                    Divider()
                }
            }
        }
    }
}