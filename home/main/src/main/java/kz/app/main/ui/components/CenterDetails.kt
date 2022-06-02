package kz.app.main.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import kz.app.components.RatingBar
import kz.app.core.domain.ProgressBarState
import kz.app.hitprint_domain.Center
import kz.app.hitprint_domain.Review
import kz.app.main.R
import java.util.*


@OptIn(ExperimentalPagerApi::class)
@Composable
fun CenterDetails(
    center: Center,
    reviews: List<Review>,
    reviewsProgress: ProgressBarState,
    onCenterSelected: (Center) -> Unit,
    onLinkCLiked: (String) -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 14.dp),
            text = center.name,
            fontSize = 17.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        RatingBar(
            rating = center.rating, modifier = Modifier
                .height(25.dp)
                .padding(horizontal = 14.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        val pagerState = rememberPagerState(0)
        val coroutine = rememberCoroutineScope()

        TabRow(
            modifier = Modifier,
            // Our selected tab is our current page
            selectedTabIndex = pagerState.currentPage,
            // Override the indicator, using the provided pagerTabIndicatorOffset modifier
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    color = Color(0xFF35A166)
                )
            },
            backgroundColor = Color.Transparent,
            contentColor = Color.Black
        ) {
            // Add tabs for all of our pages
            listOf(stringResource(id = R.string.info), stringResource(id = R.string.reviews)).forEachIndexed { index, title ->
                Tab(
                    text = { Text(title.uppercase(Locale.getDefault())) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutine.launch {
                            pagerState.scrollToPage(index)
                        }
                    },
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            count = 2
        ) { page ->
            when(page) {
                0 -> {
                    InfoTabView(center = center, onCenterSelected, onLinkCLiked)
                }
                1 -> {
                    RatingComments(reviews, reviewsProgress)
                }
            }
        }
    }
}

