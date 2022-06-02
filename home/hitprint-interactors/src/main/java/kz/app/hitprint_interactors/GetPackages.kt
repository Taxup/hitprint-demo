package kz.app.hitprint_interactors

import kotlinx.coroutines.flow.flow
import kz.app.core.domain.DataState
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.UIComponent
import kz.app.hitprint_datasource.network.HitprintService
import kz.app.hitprint_domain.Package

class GetPackages(
    private val service: HitprintService
) {

    fun execute() = flow<DataState<List<Package>>> {
        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
        try {
            val packages = service.getPackageList()
            emit(DataState.Data(packages))
        } catch (e: Exception) {
            emit(DataState.Response(UIComponent.Dialog(description = e.message.orEmpty())))
        }
        finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }

}