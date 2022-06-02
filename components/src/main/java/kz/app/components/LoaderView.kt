package kz.app.components

import android.animation.ValueAnimator
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView

val colorBackgroundLoader = Color(0xB3000000)

@Composable
fun LoaderView() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorBackgroundLoader)
            .clickable {  }
            .focusable(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AndroidView(
            modifier = Modifier
                .width(100.dp)
                .height(100.dp),
            factory = {
                LottieAnimationView(it)
            }
        ) {
            it.repeatCount = ValueAnimator.INFINITE
            it.setAnimation(R.raw.loader)
            it.speed = 1.5f
            it.playAnimation()
        }
    }
}