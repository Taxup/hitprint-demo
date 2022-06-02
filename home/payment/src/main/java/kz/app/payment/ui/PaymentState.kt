package kz.app.payment.ui

import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent
import kz.app.hitprint_domain.PayOrder

data class PaymentState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val queue: Queue<UIComponent> = Queue(),
    val order: PayOrder? = null
)
