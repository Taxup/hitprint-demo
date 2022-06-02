package kz.app.ui_upload_documents.extension

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log


fun Context.openFile(uri: Uri) {
    val type = contentResolver.getType(uri)
    val intent = Intent(Intent.ACTION_VIEW).apply {
        addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        setDataAndType(uri, type)
    }
    startActivity(intent)
}

fun Context.shareFile(uri: Uri) {
    val type = contentResolver.getType(uri)
    try {
        val intent = Intent(Intent.ACTION_SEND).apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(Intent.EXTRA_STREAM, uri)
            setType(type)
        }
        startActivity(intent)
    } catch (e: IllegalArgumentException) {
        Log.e("TAG", "shareFile: ${e.message}")
    }
}

fun Double.toMB(): String {
    return "%.2f".format(this)
}

fun Context.getFileName(uri: Uri): String {
    var name: String? = null
    contentResolver.query(uri, fileName = { name = it })
    return name.orEmpty()
}

fun Context.getFileSize(uri: Uri): Double {
    var size: Long? = null
    contentResolver.query(uri, size = { size = it })
    return size?.toDouble()?.div((1024 * 1024.0)) ?: 0.0
}

fun ContentResolver.query(
    uri: Uri,
    fileName: ((String) -> Unit)? = null,
    size: ((Long) -> Unit)? = null
) {
    query(uri, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)

        cursor.moveToFirst()
        fileName?.invoke(cursor.getString(nameIndex))
        size?.invoke(cursor.getLong(sizeIndex))
    }
}
