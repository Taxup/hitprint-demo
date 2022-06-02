package kz.app.ui_packages.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kz.app.core.domain.DataState
import kz.app.core.domain.Logger
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent
import kz.app.hitprint_interactors.GetPackages
import kz.app.hitprint_interactors.SelectPackage
import kz.app.navigation.Navigator
import kz.app.navigation.Screen
import javax.inject.Inject

@HiltViewModel
class PackagesViewModel @Inject constructor(
    private val navigator: Navigator,
    private val logger: Logger,
    private val stateHandle: SavedStateHandle,
    private val selectPackage: SelectPackage,
    private val getPackages: GetPackages
) : ViewModel() {

    val state: MutableState<PackagesState> = mutableStateOf(PackagesState())

    init {
        onTriggerEvent(PackagesEvent.GetAllAvailablePackages)
    }

    fun onTriggerEvent(event: PackagesEvent) {
        when (event) {
            is PackagesEvent.SelectPackage -> selectPackage(event.packageId)
            PackagesEvent.GetAllAvailablePackages -> getAllPackages()
            PackagesEvent.OnBackPressed -> navigator.navigateUp()
            PackagesEvent.OnNextClicked -> onNextClicked()
            PackagesEvent.OnRemoveHeadFromQueue -> removeHeadMessage()
        }
    }

    private fun onNextClicked() {
        if (state.value.selectedPackageId == null) {
            appendToMessageQueue(UIComponent.Dialog(description = "Чтобы продолжить выберите пакет"))
            return
        }
        val orderNumber = stateHandle.get<String>("orderNumber").orEmpty()
        selectPackage.execute(orderNumber, state.value.selectedPackageId!!).onEach { dataState ->
            when (dataState) {
                is DataState.Data -> {
                    navigator.navigateTo(Screen.DeliveryAddress, dataState.data?.number)
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

    private fun getAllPackages() {
        getPackages.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Data -> {
                    dataState.data?.let {
                        state.value = state.value.copy(packages = it)
                    }
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

    private fun selectPackage(packageId: Long) {
        state.value = state.value.copy(selectedPackageId = packageId)
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