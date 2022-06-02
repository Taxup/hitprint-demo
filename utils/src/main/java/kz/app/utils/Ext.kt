package kz.app.utils

import android.app.Activity
import androidx.appcompat.app.AlertDialog

fun Activity.showAlert(message: String) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .create()
}