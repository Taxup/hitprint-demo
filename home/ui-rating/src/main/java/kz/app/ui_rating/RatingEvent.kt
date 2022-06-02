package kz.app.ui_rating

sealed class RatingEvent {
    data class UpdateComment(val comment: String) : RatingEvent()
    data class UpdateRating(val rating: Int) : RatingEvent()
    object RateCenter : RatingEvent()
    object OnBackPressed : RatingEvent()
    object OnRemoveHeadFromQueue : RatingEvent()
}
