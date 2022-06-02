package kz.app.ui_order_history.ui

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent
import kz.app.hitprint_domain.OrderStage
import kz.app.hitprint_domain.PayOrder

data class OrderHistoryState(
    val queue: Queue<UIComponent> = Queue(),
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val searchValue: String = "",
    val orders: List<PayOrder> = emptyList(),
    val filteredOrders: List<PayOrder> = orders,
    val chips: List<Chip> = listOf()
)

data class Chip(
    val id: Int,
    @StringRes val textResId: Int,
    val stages: List<OrderStage>,
    val imageVector: ImageVector,
    val imageColor: Color,
    val isSelected: Boolean = false
)
