package kz.app.hitprint_demo.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kz.app.navigation.Navigator
import kz.app.navigation.Screen
import kz.app.utils.PhoneDataStore
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStore: PhoneDataStore,
    private val navigator: Navigator
) : ViewModel() {

    @InternalCoroutinesApi
    fun onTriggerEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.Navigate -> {
                viewModelScope.launch {
                    navigator.navigateTo(if (dataStore.hasSession().first()) Screen.UserAddress else Screen.Login, popUpTo = Screen.Splash, inclusive = true)
                }
            }
        }
    }

}