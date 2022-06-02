package kz.app.ui_order_history.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kz.app.core.domain.DataState
import kz.app.core.domain.Logger
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent
import kz.app.hitprint_domain.OrderStage
import kz.app.hitprint_domain.PayOrder
import kz.app.hitprint_interactors.GetOrderHistory
import kz.app.navigation.Navigator
import kz.app.navigation.Screen
import kz.app.ui_order_history.R
import kz.app.ui_order_history.ui.utils.getColorByStage
import kz.app.utils.PhoneDataStore
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val phoneDataStore: PhoneDataStore,
    private val getOrderHistory: GetOrderHistory,
    private val navigator: Navigator,
    private val logger: Logger
) :ViewModel() {

    val state: MutableState<OrderHistoryState> = mutableStateOf(OrderHistoryState())

    init {
        onTriggerEvent(OrderHistoryEvent.GetOrderHistory)
        state.value = state.value.copy(
            chips = listOf(
                Chip(
                    id = 0,
                    textResId = R.string.all,
                    stages = OrderStage.values().toList(),
                    imageVector = Icons.Rounded.DoneAll,
                    imageColor = Color.Black,
                    isSelected = true
                ),
                Chip(
                    id = 4,
                    textResId = R.string.executed,
                    stages = listOf(OrderStage.PAYMENT_DONE),
                    imageVector = Icons.Rounded.Done,
                    imageColor = getColorByStage(OrderStage.PAYMENT_DONE)
                ),
                Chip(
                    id = 1,
                    textResId = R.string.to_be_paid,
                    stages = listOf(OrderStage.READY_FOR_PAY),
                    imageVector = Icons.Rounded.CreditCard,
                    imageColor = getColorByStage(OrderStage.READY_FOR_PAY)
                ),
                Chip(
                    id = 2,
                    textResId = R.string.in_processing,
                    stages = listOf(OrderStage.PRICING),
                    imageVector = Icons.Rounded.AccessTime,
                    imageColor = getColorByStage(OrderStage.PRICING)
                ),
                Chip(
                    id = 3,
                    textResId = R.string.delivering,
                    stages = listOf(OrderStage.PAID),
                    imageVector = Icons.Rounded.LocalShipping,
                    imageColor = getColorByStage(OrderStage.PAID)
                ),

            )
        )
    }

    fun onTriggerEvent(event: OrderHistoryEvent) {
        when(event) {
            OrderHistoryEvent.GetOrderHistory -> getOrderHistory()
            OrderHistoryEvent.OnRemoveHeadFromQueue -> removeHeadMessage()
            is OrderHistoryEvent.GoToOrderDetails -> goToDetails(event.orderNumber)
            OrderHistoryEvent.FilterHistory -> filterHistory()
            is OrderHistoryEvent.UpdateSearchValue -> updateSearchValue(event.value)
            is OrderHistoryEvent.OnChipChecked -> onChipChecked(event.chipId)
        }
    }

    private fun goToDetails(orderNumber: String) {
        state.value.orders.find { it.number == orderNumber }?.let {
            when (it.stage) {
                OrderStage.PAYMENT_DONE -> navigator.navigateTo(Screen.OrderDetails, orderNumber)
                OrderStage.PAID -> navigator.navigateTo(Screen.Payment, orderNumber)
                OrderStage.READY_FOR_PAY -> navigator.navigateTo(Screen.Payment, orderNumber)
                OrderStage.PRICING -> navigator.navigateTo(Screen.OrderDetails, orderNumber)
            }
        }
    }

    private fun onChipChecked(chipId: Int) {
        val chips = state.value.chips.map {
            if (it.id == chipId) it.copy(isSelected = true)
            else it.copy(isSelected = false)
        }
        state.value = state.value.copy(chips = chips)
        filterHistory()
    }

    private fun updateSearchValue(value: String) {
        state.value = state.value.copy(searchValue = value)
    }

    private fun filterHistory() {
        val stages: List<OrderStage> = state.value.chips.filter { it.isSelected }.flatMap { it.stages }
        val search = state.value.searchValue
        val filteredOrders: List<PayOrder> = state.value.orders.filter {
            it.stage in stages && (it.businessName.contains(search, true)
                            || it.number.contains(search, true)
                            || it.price.toString().contains(search, true))
        }
        state.value = state.value.copy(filteredOrders = filteredOrders)
    }

    private fun getOrderHistory() {
        viewModelScope.launch {
            val phoneNumber = phoneDataStore.getPhone.first()
            getOrderHistory.execute("+7$phoneNumber").onEach { dataState ->
                when (dataState) {
                    is DataState.Data -> {
                        state.value = state.value.copy(orders = dataState.data.orEmpty())
                        filterHistory()
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