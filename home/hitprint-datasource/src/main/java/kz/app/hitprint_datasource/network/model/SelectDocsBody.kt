package kz.app.hitprint_datasource.network.model

import kotlinx.serialization.Serializable
import kz.app.hitprint_domain.DocUuidAndComment

@Serializable
data class SelectDocsBody(
    val orderNumber: String,
    val docUuidAndCommentList: List<DocUuidAndCommentBody>
)

@Serializable
data class DocUuidAndCommentBody(
    val docUuid: String,
    val comment: String
)

fun DocUuidAndComment.toDto() = DocUuidAndCommentBody(docUuid, comment)