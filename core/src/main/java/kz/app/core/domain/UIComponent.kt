package kz.app.core.domain

sealed class UIComponent {

    data class Dialog(
        val title: String? = null,
        val description: String,
        val positive: String = "Ok",
        val positiveAction: (() -> Unit)? = null,
        val negative: String? = null,
        val negativeAction: (() -> Unit)? = null,
    ) : UIComponent()

    data class Default(
        val message: String
    ) : UIComponent()

}