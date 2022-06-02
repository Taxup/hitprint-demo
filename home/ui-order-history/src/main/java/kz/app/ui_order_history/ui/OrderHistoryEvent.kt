package kz.app.ui_order_history.ui

sealed class OrderHistoryEvent {
    object GetOrderHistory : OrderHistoryEvent()
    object OnRemoveHeadFromQueue : OrderHistoryEvent()
    object FilterHistory : OrderHistoryEvent()

    data class GoToOrderDetails(val orderNumber: String) : OrderHistoryEvent()
    data class UpdateSearchValue(val value: String) : OrderHistoryEvent()
    data class OnChipChecked(val chipId: Int) : OrderHistoryEvent()

}
