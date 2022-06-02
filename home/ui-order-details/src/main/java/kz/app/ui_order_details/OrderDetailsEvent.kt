package kz.app.ui_order_details

sealed class OrderDetailsEvent {
    object OnBackPressed : OrderDetailsEvent()
    object OnRemoveHeadFromQueue : OrderDetailsEvent()

    data class OpenCheque(val orderNumber: String) : OrderDetailsEvent()
    data class GetOrderDetails(val orderNumber: String) : OrderDetailsEvent()

}
