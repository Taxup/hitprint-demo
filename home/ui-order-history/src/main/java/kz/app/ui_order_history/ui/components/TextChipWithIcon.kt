package kz.app.ui_order_history.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun TextChipWithIcon(
    iconVector: ImageVector,
    iconColor: Color,
    isSelected: Boolean,
    @StringRes textId: Int,
    onChecked: (Boolean) -> Unit,
    selectedColor: Color = Color(0xFF199450)
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                vertical = 2.dp,
                horizontal = 4.dp
            )
            .border(
                width = 1.dp,
                color = if (isSelected) selectedColor else Color.LightGray,
                shape = CircleShape
            )
            .background(
                color = if (isSelected) selectedColor else Color.Transparent,
                shape = CircleShape
            )
            .clip(shape = CircleShape)
            .clickable {
                onChecked(!isSelected)
            }
            .padding(
                vertical = 4.dp,
                horizontal = 8.dp
            )
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(iconColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(14.dp),
                imageVector = iconVector,
                tint = Color.White,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = textId),
            color = if (isSelected) Color.White else Color.Unspecified
        )
    }
}