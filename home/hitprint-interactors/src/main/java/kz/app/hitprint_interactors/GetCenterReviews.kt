package kz.app.hitprint_interactors

import kotlinx.coroutines.flow.flow
import kz.app.core.domain.DataState
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.UIComponent
import kz.app.hitprint_datasource.network.HitprintService
import kz.app.hitprint_domain.Review

class GetCenterReviews(
    private val service: HitprintService
) {

    fun execute(businessId: Long) = flow<DataState<List<Review>>> {
        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
        try {
            val response = service.getCenterReviews(businessId)
            emit(DataState.Data(response))
        } catch (e: Exception) {
            emit(DataState.Response(UIComponent.Dialog("Error!", e.message.orEmpty())))
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }

}