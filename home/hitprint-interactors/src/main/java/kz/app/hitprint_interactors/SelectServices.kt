package kz.app.hitprint_interactors

import kotlinx.coroutines.flow.flow
import kz.app.core.domain.DataState
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.UIComponent
import kz.app.hitprint_datasource.network.HitprintService
import kz.app.hitprint_domain.PayOrder

class SelectServices(
    private val service: HitprintService
) {

    fun execute(orderNumber: String, serviceIds: List<Long>) = flow<DataState<PayOrder>> {
        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
        try {
            val response = service.selectServices(orderNumber, serviceIds)
            emit(DataState.Data(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response<PayOrder>(
                    uiComponent = UIComponent.Dialog(

                        description = e.localizedMessage
                    )
                )
            )
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }

}