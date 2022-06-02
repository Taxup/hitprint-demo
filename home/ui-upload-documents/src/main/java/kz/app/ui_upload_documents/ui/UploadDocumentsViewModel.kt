package kz.app.ui_upload_documents.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kz.app.core.domain.DataState
import kz.app.core.domain.Logger
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent
import kz.app.hitprint_domain.DocUuidAndComment
import kz.app.hitprint_domain.Document
import kz.app.hitprint_interactors.SelectDocs
import kz.app.hitprint_interactors.UploadDocuments
import kz.app.navigation.Navigator
import kz.app.navigation.Screen
import javax.inject.Inject

@HiltViewModel
class UploadDocumentsViewModel @Inject constructor(
    private val uploadDocuments: UploadDocuments,
    private val selectDocs: SelectDocs,
    private val logger: Logger,
    private val savedStateHandle: SavedStateHandle,
    private val navigator: Navigator
) : ViewModel(){

    val state: MutableState<UploadDocumentsState> = mutableStateOf(UploadDocumentsState())

    fun onTriggerEvent(event: UploadDocumentsEvent) {
        when(event){
            is UploadDocumentsEvent.AddDocumentsUri -> addDocuments(event.files)
            UploadDocumentsEvent.OnRemoveHeadFromQueue -> removeHeadMessage()
            UploadDocumentsEvent.OnNextClicked -> selectDocuments()
            UploadDocumentsEvent.OnBackPressed -> navigator.navigateUp()
            is UploadDocumentsEvent.OnCommentDocument -> onCommentDocument(event.docId, event.comment)
            is UploadDocumentsEvent.DeleteDocument -> deleteDocument(event.doc)
        }
    }

    private fun deleteDocument(document: Document) {
        val files = state.value.files.filter { it.id != document.id }
        state.value = state.value.copy(files = files)
    }

    private fun onCommentDocument(docId: String, comment: String) {
        val files = state.value.files.map {
            if (it.id == docId) it.copy(comment = comment)
            else it
        }
        state.value = state.value.copy(files = files)
    }

    private fun selectDocuments() {
        if (state.value.files.any { it.uuid == null }) {
            appendToMessageQueue(UIComponent.Dialog("Attention!", "Files are not uploaded to server"))
            return
        }

        val orderNumber = savedStateHandle.get<String>("orderNumber").orEmpty()
        selectDocs.execute(orderNumber, state.value.files.map { DocUuidAndComment(it.uuid!!, it.comment) }).onEach { dataState ->
            when (dataState) {
                is DataState.Data -> {
                    navigator.navigateTo(Screen.SelectPackages, dataState.data?.number)
                }
                is DataState.Loading -> state.value =
                    state.value.copy(progressBarState = dataState.progressBarState)
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> appendToMessageQueue(dataState.uiComponent)
                        is UIComponent.Default -> logger.log((dataState.uiComponent as UIComponent.Default).message)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun uploadFile(document: Document) {
        uploadDocuments.execute(
            document,
            progress = { progress ->
                val files = state.value.files.map {
                    if (it.id == document.id) it.copy(progress = progress)
                    else it
                }
                state.value = state.value.copy(files = files)
            }
        ).onEach { dataState: DataState<String> ->
            when(dataState) {
                is DataState.Data -> {
                    val files = state.value.files.map {
                        if (it.id == document.id) it.copy(uuid = dataState.data ?: "")
                        else it
                    }
                    state.value = state.value.copy(files = files)
                }
                is DataState.Loading -> {
                }
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> {
                            appendToMessageQueue(dataState.uiComponent)
                        }
                        is UIComponent.Default -> {
                            val files = state.value.files.map {
                                if (it.id == document.id) {
                                    it.copy(error = (dataState.uiComponent as UIComponent.Default).message)
                                }
                                else {
                                    it
                                }
                            }
                            state.value = state.value.copy(files = files)
                            logger.log((dataState.uiComponent as UIComponent.Default).message)
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

//    private fun increaseFileAmount(file: Document) {
//        val newList = state.value.files.map {
//            if (it == file) it.copy(amount = it.amount + 1)
//            else it
//        }
//        state.value = state.value.copy(files = newList)
//    }
//
//    private fun decreaseFileAmount(file: Document) {
//        val newList = state.value.files.map {
//            if (it == file) it.copy(amount = it.amount - 1)
//            else it
//        }.filter { it.amount > 0 }
//        state.value = state.value.copy(files = newList)
//    }

    private fun addDocuments(files: List<Document>) {
        val list = state.value.files.toMutableList()
        list.addAll(files)
        state.value = state.value.copy(files = list)

        list.forEach { uploadFile(it) }
    }


    private fun appendToMessageQueue(uiComponent: UIComponent) {
        val queue = state.value.queue
        queue.add(uiComponent)
        state.value = state.value.copy(queue = Queue())
        state.value = state.value.copy(queue = queue)
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.value.queue
            queue.remove()
            state.value = state.value.copy(queue = Queue())
            state.value = state.value.copy(queue = queue)
        } catch (e: Exception) {
            logger.log(e.message.toString())
        }
    }
}