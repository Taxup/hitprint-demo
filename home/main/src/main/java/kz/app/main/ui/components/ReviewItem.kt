package kz.app.main.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.app.hitprint_domain.Review
import kz.app.main.R


@Composable
fun ReviewItem(review: Review) {
    Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
        Image(
            painter = painterResource(id = R.drawable.anon_user),
            contentDescription = "user logo",
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(8.dp)),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Anonymous",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight(300)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = review.comment,
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight(200)
            )
        }
        Text(
            text = review.dateCreated.orEmpty(),
            fontSize = 14.sp,
            color = Color(0xFFBDBDBD),
            fontWeight = FontWeight(400)
        )
    }
    Divider()
}
