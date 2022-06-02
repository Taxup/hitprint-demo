package kz.app.ui_user_address.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
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
import kz.app.hitprint_domain.PaymentMethod
import kz.app.hitprint_domain.User
import kz.app.hitprint_interactors.GetCities
import kz.app.hitprint_interactors.GetUserInfo
import kz.app.hitprint_interactors.SetDeliveryAddress
import kz.app.navigation.HomeScreen
import kz.app.navigation.Navigator
import kz.app.navigation.Screen
import kz.app.utils.PhoneDataStore
import javax.inject.Inject

@HiltViewModel
class DeliveryAddressViewModel @Inject constructor(
    private val navigator: Navigator,
    private val getUserInfo: GetUserInfo,
    private val setDeliveryAddress: SetDeliveryAddress,
    private val getCities: GetCities,
    private val dataStore: PhoneDataStore,
    private val savedStateHandle: SavedStateHandle,
    private val logger: Logger
) : ViewModel() {

    val state: MutableState<DeliveryAddressState> = mutableStateOf(DeliveryAddressState())

    init {
        viewModelScope.launch {
            val phone = "+7${dataStore.getPhone.first()}"
            onTriggerEvent(DeliveryAddressEvent.GetUserInfo(phone))
        }
        onTriggerEvent(DeliveryAddressEvent.GetAllCities)
    }

    fun onTriggerEvent(event: DeliveryAddressEvent) {
        when(event) {
            is DeliveryAddressEvent.GetUserInfo -> getUserInfo(event.phoneNumber)
            DeliveryAddressEvent.GoNext -> updateUserInfo()
            DeliveryAddressEvent.OnBackPressed -> navigator.navigateUp()
            DeliveryAddressEvent.RemoveHeadFromQueue -> removeHeadMessage()
            is DeliveryAddressEvent.UpdateAddress -> updateAddress(event.address)
            is DeliveryAddressEvent.UpdateCity -> updateCity(event.city)
            is DeliveryAddressEvent.UpdatePaymentType -> updatePaymentMethod(event.paymentMethod)
            DeliveryAddressEvent.GetAllCities -> getCities()
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
        val paymentType = state.value.paymentMethod
        savedStateHandle.get<String>("orderNumber")?.let {
            setDeliveryAddress.execute(it, city, address, paymentType).onEach { dataState ->
                when(dataState) {
                    is DataState.Data -> {
                        navigator.navigateTo(Screen.OrderCreateSuccess, dataState.data?.number, popUpTo = HomeScreen.Map)
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
    }

    private fun updatePaymentMethod(paymentMethod: PaymentMethod) {
        state.value = state.value.copy(paymentMethod = paymentMethod)
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