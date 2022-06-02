package kz.app.hitprint_domain

import java.io.InputStream
import java.util.*

data class Document(
    val id: String = UUID.randomUUID().toString(),
    val uuid: String? = null,
    val filename: String,
    val size: Double,
    val data: InputStream,
    val progress: Float = 0f,
    val error: String = "",
    val comment: String = ""
)