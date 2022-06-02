package kz.app.ui_rating

import androidx.annotation.IntRange
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent

data class RatingState(
    val queue: Queue<UIComponent> = Queue(),
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    @IntRange(from = 0, to = 5) val rating: Int = 5,
    val comment: String = ""
)
