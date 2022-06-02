package kz.app.ui_login.ui

import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent

data class LoginScreenState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val phoneNumber: String = "",
    val errorQueue: Queue<UIComponent> = Queue(),
    val phoneValidationError: String? = null
)