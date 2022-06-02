package kz.app.main.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kz.app.core.domain.ProgressBarState
import kz.app.hitprint_domain.Review
import kz.app.main.R

@Composable
fun RatingComments(reviews: List<Review>, reviewsProgress: ProgressBarState) {
    when {
        reviewsProgress == ProgressBarState.Loading -> {
            CircularProgressIndicator()
        }
        reviews.isEmpty() -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_empty_state),
                        contentDescription = "empty"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = stringResource(id = R.string.no_reviews), color = Color.LightGray)
                }
            }
        }
        else -> {
            LazyColumn {
                items(reviews) { review ->
                    ReviewItem(review)
                }
            }
        }
    }
}