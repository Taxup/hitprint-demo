package kz.app.ui_order_details

import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent
import kz.app.hitprint_domain.PayOrder

data class OrderDetailsState(
    val queue: Queue<UIComponent> = Queue(),
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val order: PayOrder? = null
)
