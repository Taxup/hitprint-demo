package kz.app.services.ui

import kz.app.hitprint_domain.PrintService
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent

data class ServiceScreenState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val queue: Queue<UIComponent> = Queue(),
    val services: List<PrintService> = emptyList(),
    val filteredServices: List<PrintService> = services,
    val serviceName: String = "",
    val selectedServiceIds: List<Long> = emptyList()
)
