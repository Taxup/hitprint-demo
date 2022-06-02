package kz.app.ui_user_info

import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent
import kz.app.hitprint_domain.PaymentMethod

data class UserAddressState(
    val queue: Queue<UIComponent> = Queue(),
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val city: String = "",
    val selectableCities: List<String> = emptyList(),
    val address: String = "",
)
