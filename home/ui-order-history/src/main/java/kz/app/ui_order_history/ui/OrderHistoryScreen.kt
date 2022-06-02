package kz.app.ui_order_history.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kz.app.components.DefaultScreen
import kz.app.ui_order_history.R
import kz.app.ui_order_history.ui.components.OrderList
import kz.app.ui_order_history.ui.components.TextChipWithIcon

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun OrderHistoryScreen(
    state: OrderHistoryState,
    events: (OrderHistoryEvent) -> Unit
) {
    DefaultScreen(
        queue = state.queue,
        onRemoveHeadFromQueue = { events.invoke(OrderHistoryEvent.OnRemoveHeadFromQueue) }
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = false),
            onRefresh = {
                events.invoke(OrderHistoryEvent.GetOrderHistory)
            }
        ) {
            Column {
                val keyboardController = LocalSoftwareKeyboardController.current
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    value = state.searchValue,
                    onValueChange = {
                        events.invoke(OrderHistoryEvent.UpdateSearchValue(it))
                        events.invoke(OrderHistoryEvent.FilterHistory)
                    },
                    label = { Text(text = stringResource(id = R.string.shared_search)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search,
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            events.invoke(OrderHistoryEvent.FilterHistory)
                            keyboardController?.hide()
                        },
                    ),
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
                )

                LazyRow {
                    items(state.chips) { chip ->
                        TextChipWithIcon(
                            iconVector = chip.imageVector,
                            iconColor = chip.imageColor,
                            isSelected = chip.isSelected,
                            textId = chip.textResId,
                            onChecked = {
                                events.invoke(OrderHistoryEvent.OnChipChecked(chip.id))
                            }
                        )
                    }
                }

                OrderList(
                    orders = state.filteredOrders,
                    progressBarState = state.progressBarState,
                    onItemClicked = {
                        events.invoke(OrderHistoryEvent.GoToOrderDetails(it))
                    }
                )
            }
        }
    }
}