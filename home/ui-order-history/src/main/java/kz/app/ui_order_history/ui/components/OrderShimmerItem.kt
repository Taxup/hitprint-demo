package kz.app.ui_order_history.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun OrderShimmerItem(brush: Brush) {

    // Column composable containing spacer shaped like a rectangle,
    // set the [background]'s [brush] with the brush receiving from [ShimmerAnimation]
    // Composable which is the Animation you are gonna create.
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .size(44.dp)
                .background(brush, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Spacer(
                modifier = Modifier
                    .size(140.dp, 14.dp)
                    .background(brush, shape = RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Spacer(
                modifier = Modifier
                    .size(120.dp, 14.dp)
                    .background(brush, shape = RoundedCornerShape(12.dp))
            )
        }
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            Spacer(
                modifier = Modifier
                    .size(100.dp, 14.dp)
                    .background(brush, shape = RoundedCornerShape(12.dp))
            )
        }
    }
    Divider()
}

