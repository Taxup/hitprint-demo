package kz.app.main.ui

import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent
import kz.app.hitprint_domain.Center
import kz.app.hitprint_domain.Location
import kz.app.hitprint_domain.Review
import kz.app.hitprint_domain.User

data class MainScreenState constructor(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val queue: Queue<UIComponent> = Queue(),
    val centers: List<Center>? = null,
    val clickedCenter: Center? = null,
    val userInfo: User? = null,
    val userLocation: Location ?= null,
    val centerReviews: List<Review> = emptyList(),
    val centerReviewsProgress: ProgressBarState = ProgressBarState.Idle,
    val zoom: Float = 14f
)
