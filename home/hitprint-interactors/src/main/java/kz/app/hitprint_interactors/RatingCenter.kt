package kz.app.hitprint_interactors

import io.ktor.http.*
import kotlinx.coroutines.flow.flow
import kz.app.core.domain.DataState
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.UIComponent
import kz.app.hitprint_datasource.network.HitprintService
import kz.app.hitprint_datasource.network.model.CreateUpdateRatingBody

class RatingCenter(
    private val service: HitprintService
) {

    fun execute(
        orderNumber: String,
        rating: Int,
        comment: String
    ) = flow<DataState<Boolean>> {
        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
        try {
            val response = service.rateCenter(
                CreateUpdateRatingBody(
                    orderNumber = orderNumber,
                    rating = rating,
                    comment = comment
                )
            )
            emit(DataState.Data(response.status.isSuccess()))
        } catch (e: Exception) {
            emit(DataState.Response(UIComponent.Dialog("Error!", e.message.orEmpty())))
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }

}