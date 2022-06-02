package kz.app.ui_user_address.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.QrCode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
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
import kz.app.hitprint_domain.PaymentMethod
import kz.app.ui_user_address.R
import kz.app.ui_user_address.ui.components.PaymentMethodItem
import java.util.*

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun DeliveryAddressScreen(
    state: DeliveryAddressState,
    events: (DeliveryAddressEvent) -> Unit
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
                    events.invoke(DeliveryAddressEvent.UpdateCity(it))
                }
            )
        }
    ) {
        DefaultScreen(
            queue = state.queue,
            progressBarState = state.progressBarState,
            toolbarText = stringResource(id = R.string.delivery_address),
            navigationAction = {
                events.invoke(DeliveryAddressEvent.OnBackPressed)
            },
            onRemoveHeadFromQueue = {
                events.invoke(DeliveryAddressEvent.RemoveHeadFromQueue)
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    modifier = Modifier.fillMaxWidth(.7f),
                    backgroundColor = Color(0xFF199450),
                    contentColor = Color.White,
                    text = {
                        Text(text = stringResource(id = R.string.shared_next).uppercase(Locale.getDefault()))
                    },
                    onClick = {
                        events.invoke(DeliveryAddressEvent.GoNext)
                    }
                )
            }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.clickable { coroutineScope.launch { sheetState.show() } }
                ) {
                    SelectCityField(city = state.city, onUpdateCity = {
                        events.invoke(DeliveryAddressEvent.UpdateCity(it))
                    })
                }
                Spacer(modifier = Modifier.height(16.dp))

                AddressEditField(
                    address = state.address,
                    onChange = { events.invoke(DeliveryAddressEvent.UpdateAddress(it)) }
                )
                Spacer(modifier = Modifier.height(16.dp))


                Text(text = stringResource(id = R.string.payment_method_is))
                listOf(
                    PaymentMethod.KaspiQR to Icons.Rounded.QrCode,
                    PaymentMethod.Cash to Icons.Rounded.AttachMoney,
                    PaymentMethod.Card to Icons.Rounded.CreditCard,
                ).forEach { (method, icon) ->
                    Row(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    events.invoke(DeliveryAddressEvent.UpdatePaymentType(method))
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                       PaymentMethodItem(selected = state.paymentMethod == method, method = method, icon = icon) {
                           events.invoke(DeliveryAddressEvent.UpdatePaymentType(method))
                       }
                    }
                }
            }
        }
    }
}
