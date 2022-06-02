package kz.app.services.ui

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
import kz.app.hitprint_interactors.FilterServices
import kz.app.hitprint_interactors.GetServices
import kz.app.hitprint_interactors.SelectServices
import kz.app.navigation.Navigator
import kz.app.navigation.Screen
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val getServices: GetServices,
    private val selectServices: SelectServices,
    private val filterServices: FilterServices,
    private val logger: Logger,
    private val navigator: Navigator,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state: MutableState<ServiceScreenState> = mutableStateOf(ServiceScreenState())

    init {
        onTriggerEvent(ServiceScreenEvent.GetAllServices)
    }

    fun onTriggerEvent(event: ServiceScreenEvent) {
        when (event) {
            ServiceScreenEvent.GetAllServices -> getAllServices()
            ServiceScreenEvent.OnRemoveHeadFromQueue -> removeHeadMessage()
            is ServiceScreenEvent.SelectService -> selectService(event.serviceId)
            ServiceScreenEvent.FilterServices -> filterServices()
            is ServiceScreenEvent.UpdateServiceName -> updateServiceName(event.serviceName)
            ServiceScreenEvent.OnBackPressed -> navigator.navigateUp()
            ServiceScreenEvent.OnNextClicked -> selectServices()
        }
    }

    private fun selectServices() {
        val orderNumber = savedStateHandle.get<String>("orderNumber").orEmpty()
        selectServices.execute(orderNumber, state.value.selectedServiceIds).onEach { dataState ->
            when (dataState) {
                is DataState.Data -> {
                    navigator.navigateTo(Screen.UploadDocuments, dataState.data?.number)
                }
                is DataState.Loading -> state.value =
                    state.value.copy(progressBarState = dataState.progressBarState)
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> appendToMessageQueue(dataState.uiComponent)
                        is UIComponent.Default -> logger.log((dataState.uiComponent as UIComponent.Default).message)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun updateServiceName(serviceName: String) {
        state.value = state.value.copy(serviceName = serviceName)
    }

    private fun filterServices() {
        val filteredList = filterServices.execute(
            current = state.value.services,
            serviceName = state.value.serviceName
        )
        state.value = state.value.copy(filteredServices = filteredList)
    }

    private fun selectService(serviceId: Long) {
        val services = state.value.services.map {
            if (it.id == serviceId) it.copy(selected = !it.selected) else it
        }
        state.value = state.value.copy(
            services = services,
            selectedServiceIds = services.filter { it.selected }.map { it.id })
        filterServices()
    }

    private fun getAllServices() {
        getServices.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Data -> {
                    state.value = state.value.copy(services = dataState.data ?: emptyList())
                    filterServices()
                }
                is DataState.Loading -> state.value =
                    state.value.copy(progressBarState = dataState.progressBarState)
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> appendToMessageQueue(dataState.uiComponent)
                        is UIComponent.Default -> logger.log((dataState.uiComponent as UIComponent.Default).message)
                    }
                }
            }
        }.launchIn(viewModelScope)
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
