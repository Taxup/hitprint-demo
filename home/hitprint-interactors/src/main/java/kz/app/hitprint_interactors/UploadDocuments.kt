package kz.app.hitprint_interactors

import kotlinx.coroutines.flow.flow
import kz.app.hitprint_datasource.network.HitprintService
import kz.app.hitprint_domain.Document
import kz.app.core.domain.DataState
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.UIComponent
import java.lang.Exception

class UploadDocuments(
    private val service: HitprintService
) {

    fun execute(document: Document, progress: (Float) -> Unit) = flow<DataState<String>> {
        emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
        try {
            val response = service.uploadDocument(document, onUpload = { a, b ->
                progress.invoke(a.toFloat()/b.toFloat())
            })
            emit(DataState.Data(response))
        } catch (e: Exception) {
            emit(DataState.Response(uiComponent = UIComponent.Default(message = e.message.toString())))
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }

}