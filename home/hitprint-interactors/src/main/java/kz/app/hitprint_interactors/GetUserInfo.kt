package kz.app.hitprint_interactors

import kotlinx.coroutines.flow.flow
import kz.app.core.domain.DataState
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.UIComponent
import kz.app.hitprint_datasource.network.HitprintService
import kz.app.hitprint_domain.User

class GetUserInfo(
    private val service: HitprintService
) {

    fun execute(phoneNumber: String) = flow<DataState<User>> {
        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
        try {
            val response = service.getUserInfo(phoneNumber)
            emit(DataState.Data(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                DataState.Response(
                    uiComponent = UIComponent.Dialog( description = e.message.orEmpty())
                )
            )
        }
        finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }

}