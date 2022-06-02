package kz.app.ui_upload_documents.ui

import kz.app.hitprint_domain.Document
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent


data class UploadDocumentsState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val queue: Queue<UIComponent> = Queue(),
    val files: List<Document> = emptyList()
)



