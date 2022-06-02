package kz.app.hitprint_interactors

import kotlinx.coroutines.flow.flow
import kz.app.core.domain.DataState
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.UIComponent
import kz.app.hitprint_datasource.cache.HitprintCache
import kz.app.hitprint_domain.PayOrder

class GetOrderDetails(
    private val cache: HitprintCache
) {

    fun execute(orderNumber: String) = flow<DataState<PayOrder>> {
        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
        try {
            val order = cache.getOrderByNumber(orderNumber)
            emit(DataState.Data(order))
        } catch (e: Exception) {
            emit(DataState.Response(UIComponent.Dialog("Error!", e.message.orEmpty())))
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }

}
