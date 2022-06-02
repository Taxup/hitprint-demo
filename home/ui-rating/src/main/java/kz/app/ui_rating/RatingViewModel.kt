package kz.app.ui_rating

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kz.app.core.domain.DataState
import kz.app.core.domain.Logger
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent
import kz.app.hitprint_interactors.RatingCenter
import kz.app.navigation.HomeScreen
import kz.app.navigation.Navigator
import kz.app.navigation.Screen
import javax.inject.Inject

@HiltViewModel
class RatingViewModel @Inject constructor(
    private val navigator: Navigator,
    private val ratingCenter: RatingCenter,
    private val savedStateHandle: SavedStateHandle,
    private val logger: Logger
) : ViewModel() {

    val state: MutableState<RatingState> = mutableStateOf(RatingState())

    fun onTriggerEvent(event: RatingEvent) {
        when(event) {
            RatingEvent.OnBackPressed -> navigator.navigateUp()
            RatingEvent.OnRemoveHeadFromQueue -> removeHeadMessage()
            RatingEvent.RateCenter -> rateCenter()
            is RatingEvent.UpdateComment -> updateComment(event.comment)
            is RatingEvent.UpdateRating -> updateRating(event.rating)
        }
    }

    private fun updateRating(rating: Int) {
        state.value = state.value.copy(rating = rating)
    }

    private fun updateComment(comment: String) {
        state.value = state.value.copy(comment = comment)
    }

    private fun rateCenter() {
        val orderNumber = savedStateHandle.get<String>("orderNumber").orEmpty()
        ratingCenter.execute(orderNumber, state.value.rating, state.value.comment).onEach { dataState ->
            when(dataState) {
                is DataState.Data -> {
                    if (dataState.data == true) {
                        navigator.navigateTo(HomeScreen.Map, popUpTo = Screen.Payment, inclusive = true)
                    }
                }
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> appendToMessageQueue(dataState.uiComponent)
                        is UIComponent.Default -> logger.log((dataState.uiComponent as UIComponent.Default).message)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun appendToMessageQueue(uiComponent: UIComponent = UIComponent.Dialog("", "Something went wrong")) {
        val queue = state.value.queue
        queue.add(uiComponent)
        state.value = state.value.copy(queue = Queue())
        state.value = state.value.copy(queue = queue)
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.value.queue
            queue.remove()
            state.value = state.value.copy(queue = Queue())
            state.value = state.value.copy(queue = queue)
        } catch (e: Exception) {
            logger.log(e.message.toString())
        }
    }

}