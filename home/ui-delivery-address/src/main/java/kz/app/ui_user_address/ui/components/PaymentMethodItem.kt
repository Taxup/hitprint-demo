package kz.app.ui_user_address.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kz.app.hitprint_domain.PaymentMethod

@Composable
fun PaymentMethodItem(
    selected: Boolean,
    method: PaymentMethod,
    icon: ImageVector,
    onCLick: () -> Unit) {
    RadioButton(
        selected = selected,
        onClick = onCLick,
        enabled = true,
        modifier = Modifier.size(24.dp),
        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF199450))
    )
    Text(text = method.value, modifier = Modifier.padding(start = 12.dp))
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = if (selected) Color(0xFF199450) else Color.Gray,
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = icon,
                contentDescription = method.name,
                tint = Color.White
            )
        }
    }
}