package kz.app.ui_order_history.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kz.app.components.ShimmerAnimation
import kz.app.core.domain.ProgressBarState
import kz.app.hitprint_domain.PayOrder
import kz.app.ui_order_history.R

@ExperimentalMaterialApi
@Composable
fun OrderList(
    orders: List<PayOrder>,
    progressBarState: ProgressBarState,
    onItemClicked: (orderNumber: String) -> Unit
) {
    when {
        progressBarState == ProgressBarState.Loading -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(12) {
                    ShimmerAnimation { OrderShimmerItem(brush = it) }
                }
            }
        }
        orders.isEmpty() -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_empty_state),
                    contentDescription = "empty"
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.no_order_history),
                    color = Color.LightGray
                )
            }
        }
        else -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(orders) {
                    OrderHistoryItem(order = it, onItemClicked)
                }
            }
        }
    }
}