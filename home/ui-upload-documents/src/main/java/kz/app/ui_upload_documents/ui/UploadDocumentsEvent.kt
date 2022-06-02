package kz.app.ui_upload_documents.ui

import kz.app.hitprint_domain.Document

sealed class UploadDocumentsEvent {

    data class AddDocumentsUri(val files: List<Document>) : UploadDocumentsEvent()

    data class DeleteDocument(val doc: Document) : UploadDocumentsEvent()

    object OnRemoveHeadFromQueue : UploadDocumentsEvent()

    object OnNextClicked : UploadDocumentsEvent()

    object OnBackPressed : UploadDocumentsEvent()

    data class OnCommentDocument(
        val docId: String,
        val comment: String
    ) : UploadDocumentsEvent()

}
