package kz.app.hitprint_interactors

import kotlinx.coroutines.flow.flow
import kz.app.core.domain.DataState
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.UIComponent
import kz.app.hitprint_datasource.network.HitprintService
import kz.app.hitprint_datasource.network.model.SetDeliveryAddressBody
import kz.app.hitprint_domain.PayOrder
import kz.app.hitprint_domain.PaymentMethod

class SetDeliveryAddress(
    private val service: HitprintService
) {

    fun execute(orderNumber: String, city: String, address: String, paymentMethod: PaymentMethod) =
        flow<DataState<PayOrder>> {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
            try {
                val response = service.setDeliveryAddress(
                    SetDeliveryAddressBody(
                        orderNumber,
                        city,
                        address,
                        paymentMethod
                    )
                )
                emit(DataState.Data(response))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Response(
                        uiComponent = UIComponent.Dialog(

                            description = e.message.orEmpty()
                        )
                    )
                )
            } finally {
                emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
            }
        }

}