package kz.app.services.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun ServiceShimmerItem(brush: Brush) {

    // Column composable containing spacer shaped like a rectangle,
    // set the [background]'s [brush] with the brush receiving from [ShimmerAnimation]
    // Composable which is the Animation you are gonna create.
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .width(120.dp)
                .height(90.dp)
                .background(brush = brush),
        )
        Column(
            modifier = Modifier.fillMaxWidth(0.75f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp)
                    .height(20.dp)
                    .background(brush = brush),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(.60f)
                    .padding(start = 12.dp)
                    .height(20.dp)
                    .background(brush = brush),
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 12.dp),
            horizontalAlignment = Alignment.End
        ) {
            Spacer(
                modifier = Modifier
                    .width(30.dp)
                    .height(20.dp)
                    .background(brush = brush),
            )
        }
    }
}