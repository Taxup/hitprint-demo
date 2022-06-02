package kz.app.ui_user_info

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kz.app.core.domain.DataState
import kz.app.core.domain.Logger
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent
import kz.app.hitprint_domain.User
import kz.app.hitprint_interactors.GetCities
import kz.app.hitprint_interactors.GetUserInfo
import kz.app.hitprint_interactors.SetUserAddress
import kz.app.navigation.HomeScreen
import kz.app.navigation.Navigator
import kz.app.navigation.Screen
import kz.app.utils.PhoneDataStore
import javax.inject.Inject

@HiltViewModel
class UserAddressViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getUserInfo: GetUserInfo,
    private val setUserAddress: SetUserAddress,
    private val getCities: GetCities,
    private val dataStore: PhoneDataStore,
    private val logger: Logger
) : ViewModel() {

    val state: MutableState<UserAddressState> = mutableStateOf(UserAddressState())

    private lateinit var phoneNumber: String

    init {
        viewModelScope.launch {
            phoneNumber = "+7${dataStore.getPhone.first()}"
            onTriggerEvent(UserAddressEvent.GetUserInfo(phoneNumber))
        }
        onTriggerEvent(UserAddressEvent.GetAllCities)
    }

    fun onTriggerEvent(event: UserAddressEvent) {
        when(event) {
            is UserAddressEvent.GetUserInfo -> getUserInfo(event.phoneNumber)
            UserAddressEvent.SetUserAddress -> updateUserInfo()
            UserAddressEvent.OnBackPressed -> navigator.navigateUp()
            UserAddressEvent.RemoveHeadFromQueue -> removeHeadMessage()
            is UserAddressEvent.UpdateAddress -> updateAddress(event.address)
            is UserAddressEvent.UpdateCity -> updateCity(event.city)
            UserAddressEvent.GetAllCities -> getCities()
        }
    }

    private fun getCities() {
        getCities.execute().onEach { dataState ->
            when(dataState) {
                is DataState.Data -> {
                    state.value = state.value.copy(selectableCities = dataState.data.orEmpty())
                }
                is DataState.Loading -> {
                }
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> appendToMessageQueue(dataState.uiComponent)
                        is UIComponent.Default -> logger.log((dataState.uiComponent as UIComponent.Default).message)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getUserInfo(phoneNumber: String) {
        getUserInfo.execute(phoneNumber).onEach { dataState: DataState<User> ->
            when(dataState) {
                is DataState.Data -> {
                    state.value = state.value.copy(
                        city = dataState.data?.city.orEmpty(),
                        address = dataState.data?.address.orEmpty(),
                    )
                }
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> appendToMessageQueue(dataState.uiComponent)
                        is UIComponent.Default -> logger.log((dataState.uiComponent as UIComponent.Default).message)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateUserInfo() {
        val city = state.value.city
        val address = state.value.address
        setUserAddress.execute(phoneNumber, city, address).onEach { dataState ->
            when(dataState) {
                is DataState.Data -> {
                    navigator.navigateTo(HomeScreen.Map, popUpTo = Screen.UserAddress, inclusive = true)
                }
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> appendToMessageQueue(dataState.uiComponent)
                        is UIComponent.Default -> logger.log((dataState.uiComponent as UIComponent.Default).message)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateCity(city: String) {
        state.value = state.value.copy(city = city)
    }

    private fun updateAddress(address: String) {
        state.value = state.value.copy(address = address)
    }

    private fun appendToMessageQueue(uiComponent: UIComponent) {
        val queue = state.value.queue
        queue.add(uiComponent)
        state.value = state.value.copy(queue = Queue())
        state.value = state.value.copy(queue = queue)
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.value.queue
            queue.remove()
            state.value = state.value.copy(queue = Queue())
            state.value = state.value.copy(queue = queue)
        } catch (e: Exception) {
            logger.log(e.message.toString())
        }
    }
}