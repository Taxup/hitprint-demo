package kz.app.hitprint_interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.app.core.domain.DataState
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.UIComponent
import kz.app.hitprint_datasource.network.HitprintService
import kz.app.hitprint_domain.Center

class GetCenters(
    private val service: HitprintService
) {

    fun execute(city: String): Flow<DataState<List<Center>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val centers: List<Center> = try {
                service.getCenterList(city)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(
                    DataState.Response<List<Center>>(
                        uiComponent = UIComponent.Dialog(

                            description = e.localizedMessage
                        )
                    )
                )
                emptyList()
            }

            emit(DataState.Data(centers))
        } catch (e: Exception) {
            e.printStackTrace()
//            logger.log(e.localizedMessage)
            emit(
                DataState.Response<List<Center>>(
                    uiComponent = UIComponent.Dialog(

                        description = e.localizedMessage
                    )
                )
            )
        }
        finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }

}
