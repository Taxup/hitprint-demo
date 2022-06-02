package kz.app.core.domain

class Logger(
    val tag: String,
    val isDebug: Boolean = true
) {

    fun log(message: String) {
        if (isDebug) {
            printLogD(tag, message)
        } else {
            // log when production
        }
    }

    companion object Factory {
        fun buildDebug(tag: String) : Logger = Logger(tag, isDebug = true)
        fun buildRelease(tag: String) : Logger = Logger(tag, isDebug = false)
    }
}

fun printLogD(tag: String, message: String) {
    println("$tag: $message")
}