package kz.app.ui_wait.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kz.app.core.domain.Logger
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent
import kz.app.hitprint_interactors.GetOrderState
import kz.app.navigation.HomeScreen
import kz.app.navigation.Navigator
import kz.app.navigation.Screen
import javax.inject.Inject

@HiltViewModel
class OrderCreateSuccessViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getOrderState: GetOrderState,
    private val logger: Logger
) : ViewModel() {

    val state: MutableState<OrderCreateSuccessState> = mutableStateOf(OrderCreateSuccessState())

    init {
    }

    fun onTriggerEvent(event: OrderCreateSuccessEvent) {
        when (event) {
            OrderCreateSuccessEvent.OnRemoveHeadFromQueue -> removeHeadMessage()
            is OrderCreateSuccessEvent.GoMain -> navigator.navigateTo(HomeScreen.Map, popUpTo = Screen.OrderCreateSuccess, inclusive = true)
        }
    }

//    private fun getOrderState(orderNumber: String) {
//        viewModelScope.launch {
//            getOrderState.execute(orderNumber)
//                .retry {
//                    delay(2000)
//                    it is WaitException
//                }
//                .catch {
//                    logger.log(it.message.orEmpty())
//                }
//                .collect { dataState ->
//                    when (dataState) {
//                        is DataState.Data -> {
//                            if (dataState.data?.stage == OrderStage.READY_FOR_PAY) {
//                                navigator.navigateTo(Screen.Payment, orderNumber)
//                            }
//                        }
//                        is DataState.Loading -> Unit
//                        is DataState.Response -> appendToMessageQueue(dataState.uiComponent)
//                    }
//                }
//        }
//    }

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
