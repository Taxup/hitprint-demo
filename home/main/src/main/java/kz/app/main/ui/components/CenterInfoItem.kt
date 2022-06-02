package kz.app.main.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CenterInfoItem(
    title: String,
    description: String,
    imageVector: ImageVector,
    descriptionStyle: TextStyle = LocalTextStyle.current,
    onClick: () -> Unit = {}
) {
    Column {
        Spacer(modifier = Modifier.height(20.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(13.dp))
            Icon(
                modifier = Modifier
                    .size(27.dp),
                imageVector = imageVector,
                contentDescription = null,
                tint = Color(0xFF35A166),
            )
            Spacer(modifier = Modifier.width(18.dp))
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight(300),
                    fontSize = 15.sp
                )
                Text(
                    modifier = Modifier.clickable(onClick = onClick),
                    text = description,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight(200),
                    fontSize = 15.sp,
                    style = descriptionStyle
                )
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
    }
}