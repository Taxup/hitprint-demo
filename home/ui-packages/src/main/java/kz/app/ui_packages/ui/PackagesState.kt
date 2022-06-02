package kz.app.ui_packages.ui

import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent
import kz.app.hitprint_domain.Package


data class PackagesState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val packages: List<Package> = emptyList(),
    val queue: Queue<UIComponent> = Queue(),
    val selectedPackageId: Long? = null
)

