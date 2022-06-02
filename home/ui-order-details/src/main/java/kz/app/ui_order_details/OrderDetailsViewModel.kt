package kz.app.ui_order_details

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
import kz.app.hitprint_interactors.GetOrderDetails
import kz.app.navigation.Navigator
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val getOrderState: GetOrderDetails,
    private val navigator: Navigator,
    private val logger: Logger,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state: MutableState<OrderDetailsState> = mutableStateOf(OrderDetailsState())

    init {
        savedStateHandle.get<String>("orderNumber")?.let {
            onTriggerEvent(OrderDetailsEvent.GetOrderDetails(it))
        }
    }

    fun onTriggerEvent(event: OrderDetailsEvent) {
        when (event) {
            OrderDetailsEvent.OnBackPressed -> navigator.navigateUp()
            is OrderDetailsEvent.OpenCheque -> TODO()
            is OrderDetailsEvent.GetOrderDetails -> getOrderDetails(event.orderNumber)
            OrderDetailsEvent.OnRemoveHeadFromQueue -> removeHeadMessage()
        }
    }

    private fun getOrderDetails(orderNumber: String) {
        getOrderState.execute(orderNumber).onEach { dataState ->
            when (dataState) {
                is DataState.Data -> {
                    state.value = state.value.copy(order = dataState.data)
                }
                is DataState.Loading -> state.value = state.value.copy(progressBarState = dataState.progressBarState)
                is DataState.Response -> appendToMessageQueue(dataState.uiComponent)
            }
        }.launchIn(viewModelScope)
    }

    private fun appendToMessageQueue(uiComponent: UIComponent) {
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