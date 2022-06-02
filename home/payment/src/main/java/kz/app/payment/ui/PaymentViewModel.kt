package kz.app.payment.ui

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
import kz.app.hitprint_interactors.FinishOrder
import kz.app.hitprint_interactors.GetOrderState
import kz.app.navigation.Navigator
import kz.app.navigation.Screen
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getOrderState: GetOrderState,
    private val finishOrder: FinishOrder,
    private val logger: Logger,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val state: MutableState<PaymentState> = mutableStateOf(PaymentState())

    init {
        val orderNumber = savedStateHandle.get<String>("orderNumber").orEmpty()
        onTriggerEvent(PaymentScreenEvent.GetOrderState(orderNumber))
    }
    fun onTriggerEvent(event: PaymentScreenEvent) {
        when(event) {
            PaymentScreenEvent.OnBackPressed -> navigator.navigateUp()
            PaymentScreenEvent.OnRemoveHeadFromQueue -> removeHeadMessage()
            is PaymentScreenEvent.GetOrderState -> getOrderState(event.orderNumber)
            PaymentScreenEvent.RateCenter -> rateCenter()
        }
    }

    private fun rateCenter() {
        val order = state.value.order
        finishOrder.execute(orderNumber = order?.number.orEmpty()).onEach { dataState ->
            when (dataState) {
                is DataState.Data -> {
                    if (dataState.data == true) {
                        navigator.navigateTo(Screen.Rating, order?.number)
                    }
                }
                is DataState.Loading -> state.value = state.value.copy(progressBarState = dataState.progressBarState)
                is DataState.Response -> appendToMessageQueue(dataState.uiComponent)
            }
        }.launchIn(viewModelScope)
    }

    private fun getOrderState(orderNumber: String) {
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