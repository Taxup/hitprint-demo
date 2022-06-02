package kz.app.ui_wait.ui

import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent

data class OrderCreateSuccessState(
    val queue: Queue<UIComponent> = Queue()
)
