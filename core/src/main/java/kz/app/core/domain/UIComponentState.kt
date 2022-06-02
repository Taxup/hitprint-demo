package kz.app.core.domain

sealed class UIComponentState {

    object Show: UIComponentState()

    object Hide: UIComponentState()

}
