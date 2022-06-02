package kz.app.ui_wait.ui

sealed class OrderCreateSuccessEvent {

    object GoMain : OrderCreateSuccessEvent()

    object OnRemoveHeadFromQueue : OrderCreateSuccessEvent()

}
