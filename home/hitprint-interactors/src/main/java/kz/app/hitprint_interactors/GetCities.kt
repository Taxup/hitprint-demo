package kz.app.hitprint_interactors

import kotlinx.coroutines.flow.flow
import kz.app.core.domain.DataState
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.UIComponent
import kz.app.hitprint_datasource.network.HitprintService

class GetCities(
    private val service: HitprintService
) {

    fun execute() = flow<DataState<List<String>>> {
        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
        try {
            val response = service.getAllCities()
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