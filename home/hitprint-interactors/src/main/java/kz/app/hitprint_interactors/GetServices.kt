package kz.app.hitprint_interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.app.hitprint_datasource.network.HitprintService
import kz.app.hitprint_domain.PrintService
import kz.app.core.domain.DataState
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.UIComponent

class GetServices(
    private val service: HitprintService
) {

    fun execute(): Flow<DataState<List<PrintService>>> = flow {
        try {
            emit(DataState.Loading<List<PrintService>>(progressBarState = ProgressBarState.Loading))

            val services: List<PrintService> = try {
                service.getServiceList()
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Response<List<PrintService>>(
                        uiComponent = UIComponent.Dialog(
                            "",
                            e.localizedMessage.toString()
                        )
                    )
                )
                emptyList()
            }

            emit(DataState.Data(services))

        } catch (e: Exception) {
            e.printStackTrace()
//            logger.log(e.localizedMessage)
            emit(
                DataState.Response<List<PrintService>>(
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