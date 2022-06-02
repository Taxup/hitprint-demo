package kz.app.ui_user_info

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kz.app.components.DefaultBottomSheet
import kz.app.components.DefaultScreen
import kz.app.components.address.AddressEditField
import kz.app.components.address.SelectCityField
import kz.app.components.address.SelectCityView
import java.util.*

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun UserAddressScreen(
    state: UserAddressState,
    events: (UserAddressEvent) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    DefaultBottomSheet(
        sheetState = sheetState,
        sheetContent = {
            SelectCityView(
                selectableCities = state.selectableCities,
                onCitySelected = {
                    coroutineScope.launch { sheetState.hide() }
                    events.invoke(UserAddressEvent.UpdateCity(it))
                }
            )
        }
    ) {
        DefaultScreen(
            queue = state.queue,
            progressBarState = state.progressBarState,
            toolbarText = stringResource(id = R.string.address),
            navigationAction = {
                events.invoke(UserAddressEvent.OnBackPressed)
            },
            onRemoveHeadFromQueue = {
                events.invoke(UserAddressEvent.RemoveHeadFromQueue)
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    modifier = Modifier.fillMaxWidth(.7f),
                    backgroundColor = Color(0xFF199450),
                    contentColor = Color.White,
                    text = {
                        Text(text = stringResource(id = R.string.set_address).uppercase(Locale.getDefault()))
                    },
                    onClick = {
                        events.invoke(UserAddressEvent.SetUserAddress)
                    }
                )
            }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.clickable { coroutineScope.launch { sheetState.show() } }
                ) {
                    SelectCityField(city = state.city, onUpdateCity = {
                        events.invoke(UserAddressEvent.UpdateCity(it))
                    })
                }
                Spacer(modifier = Modifier.height(16.dp))

                AddressEditField(
                    address = state.address,
                    onChange = { events.invoke(UserAddressEvent.UpdateAddress(it)) }
                )
            }
        }
    }
}
