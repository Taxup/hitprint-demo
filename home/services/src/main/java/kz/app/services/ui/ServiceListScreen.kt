package kz.app.services.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import kz.app.components.DefaultScreen
import kz.app.hitprint_domain.PrintService
import kz.app.services.R
import kz.app.services.ui.components.ServiceListItem
import java.util.*

@ExperimentalComposeUiApi
@OptIn(ExperimentalCoilApi::class)
@Composable
fun ServiceListScreen(
    state: ServiceScreenState,
    imageLoader: ImageLoader,
    events: (ServiceScreenEvent) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    DefaultScreen(
        queue = state.queue,
        progressBarState = state.progressBarState,
        toolbarText = stringResource(id = R.string.services),
        navigationAction = {
            events.invoke(ServiceScreenEvent.OnBackPressed)
        },
        onRemoveHeadFromQueue = {
            events.invoke(ServiceScreenEvent.OnRemoveHeadFromQueue)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.fillMaxWidth(.7f),
                backgroundColor = Color(0xFF199450),
                contentColor = Color.White,
                text = {
                    Text(text = stringResource(id = R.string.shared_select_something, state.selectedServiceIds.size).uppercase(Locale.getDefault()))
                },
                onClick = {
                    events.invoke(ServiceScreenEvent.OnNextClicked)
                }
            )
        }
    ) {
        Column {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = state.serviceName,
                onValueChange = {
                    events.invoke(ServiceScreenEvent.UpdateServiceName(it))
                    events.invoke(ServiceScreenEvent.FilterServices)
                },
                label = { Text(text = stringResource(id = R.string.shared_search)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        events.invoke(ServiceScreenEvent.FilterServices)
                        keyboardController?.hide()
                    },
                ),
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") },
                textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
            )

            LazyColumn {
                items(state.filteredServices) { service: PrintService ->
                    ServiceListItem(
                        service = service,
                        imageLoader = imageLoader,
                        isSelected = state.selectedServiceIds.contains(service.id),
                        onSelectService = { id: Long ->
                            events.invoke(ServiceScreenEvent.SelectService(id))
                        }
                    )
                }
            }
        }
    }
}