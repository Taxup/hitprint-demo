package kz.app.hitprint_interactors

import kotlinx.coroutines.flow.flow
import kz.app.core.domain.DataState
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.UIComponent
import kz.app.hitprint_datasource.cache.HitprintCache
import kz.app.hitprint_datasource.network.HitprintService
import kz.app.hitprint_domain.PayOrder

class GetOrderHistory(
    private val service: HitprintService,
    private val cache: HitprintCache
) {

    fun execute(phoneNumber: String) = flow<DataState<List<PayOrder>>> {
        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
        try {
            val response = service.getOrderHistory(phoneNumber)
            cache.insertOrders(response)
            val orders = cache.getOrderList()
            emit(DataState.Data(data = orders))
        } catch (e: Exception) {
            emit(DataState.Response(uiComponent = UIComponent.Dialog("Error!", description = e.message.orEmpty())))
        }
        finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }

}