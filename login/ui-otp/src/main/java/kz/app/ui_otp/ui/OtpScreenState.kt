package kz.app.ui_otp.ui

import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent

data class OtpScreenState(
    val code: String = "",
    val retry: Boolean = false,
    val errorMessage: String? = null,
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val errorQueue: Queue<UIComponent> = Queue()
)