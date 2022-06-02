package kz.app.hitprint_interactors

import io.ktor.http.*
import kotlinx.coroutines.flow.flow
import kz.app.core.domain.DataState
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.UIComponent
import kz.app.hitprint_datasource.network.HitprintService

class CreateUser(
    private val service: HitprintService
) {

    fun execute(phoneNumber: String, token: String) = flow<DataState<Boolean>>{
        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
        try {
            val response = service.createUpdateUser("+7$phoneNumber", token)
            emit(DataState.Data(response.status.isSuccess()))
        } catch (e: Exception) {
            emit(DataState.Response(uiComponent = UIComponent.Dialog( description = e.message.toString())))
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }

}