package kz.app.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kz.app.utils.Quadruple

class Navigator {

    private val _sharedFlow = MutableSharedFlow<Quadruple<Screen, Any?, Screen?, Boolean>>(extraBufferCapacity = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    private val _sharedBackFlow = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
    val sharedBackFlow = _sharedBackFlow.asSharedFlow()

    fun navigateTo(navTarget: Screen, args: Any? = null, popUpTo: Screen? = null, inclusive: Boolean = false) {
        _sharedFlow.tryEmit(Quadruple(navTarget, args, popUpTo, inclusive))
    }

    fun navigateUp() {
        _sharedBackFlow.tryEmit(true)
    }

}