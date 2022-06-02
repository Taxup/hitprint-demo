package kz.app.payment.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.LocalShipping
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch
import kz.app.components.DefaultScreen
import kz.app.core.domain.Currency
import kz.app.hitprint_domain.OrderStage
import kz.app.hitprint_domain.PaymentMethod
import kz.app.payment.R
import java.util.*


@ExperimentalFoundationApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun PaymentScreen(
    state: PaymentState,
    events: (PaymentScreenEvent) -> Unit,
    imageLoader: ImageLoader
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Column(modifier = Modifier.defaultMinSize(1.dp)) {
                Spacer(modifier = Modifier.height(1.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    modifier = Modifier
                        .size(60.dp, 6.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(3.dp)),
                    painter = ColorPainter(Color(0xFFBDBDBD)),
                    contentDescription = null
                )
                val painter = rememberImagePainter(
                    data = state.order?.kaspiQrUrl,
                    imageLoader = imageLoader,
                )

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .border(width = 0.5.dp, color = Color.LightGray)
                        .clickable {
                            val url = "${state.order?.kaspiQrUrl}"
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(url)
                            context.startActivity(i)
                        }
                        .background(Color.LightGray),
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    ) {
        DefaultScreen(
            queue = state.queue,
            progressBarState = state.progressBarState,
            toolbarText = stringResource(id = R.string.payment),
            navigationAction = {
                events.invoke(PaymentScreenEvent.OnBackPressed)
            },
            onRemoveHeadFromQueue = {
                events.invoke(PaymentScreenEvent.OnRemoveHeadFromQueue)
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    modifier = Modifier.fillMaxWidth(.7f),
                    backgroundColor = Color(0xFF199450),
                    contentColor = Color.White,
                    text = { Text(text = stringResource(id = R.string.confirm_receipt).uppercase(
                        Locale.getDefault())) },
                    onClick = { events.invoke(PaymentScreenEvent.RateCenter) }
                )
            }
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                if (state.order != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(getColorByStage(stage = state.order.stage), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(14.dp),
                                imageVector = getIconByStage(state.order.stage),
                                tint = Color.White,
                                contentDescription = null
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = stringResource(getTextIdByStage(state.order.stage)),
                            color = getColorByStage(state.order.stage)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Text(
                    text = state.order?.startDate.orEmpty(),
                    fontSize = 17.sp,
                    fontWeight = FontWeight(300),
                    color = Color(0xFF222222)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(
                        id = R.string.order_number_is,
                        state.order?.number.orEmpty()
                    ),
                    fontSize = 17.sp,
                    fontWeight = FontWeight(300),
                    color = Color(0xFF222222)
                )

                Spacer(modifier = Modifier.height(36.dp))

                Text(
                    text = stringResource(id = R.string.delivery_details),
                    fontSize = 17.sp,
                    color = Color(0xFF151522)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(
                        id = R.string.execution_time,
                        state.order?.executionTime ?: "Undefined"
                    ),
                    fontSize = 15.sp,
                    color = Color(0xFF151522)
                )
                Text(
                    text = stringResource(
                        id = R.string.cost,
                        "${state.order?.price ?: "Undefined "}${Currency.KZT.currencySymbol}"
                    ),
                    fontSize = 15.sp,
                    color = Color(0xFF151522)
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = stringResource(id = R.string.shipping_address),
                    fontSize = 17.sp,
                    color = Color(0xFF151522)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Almaty",
                    fontSize = 15.sp,
                    color = Color(0xFF151522)
                )
                Text(
                    text = "${state.order?.deliveryAddress}",
                    fontSize = 15.sp,
                    color = Color(0xFF151522)
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = stringResource(id = R.string.payment),
                    fontSize = 17.sp,
                    color = Color(0xFF151522)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Divider()
                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier.height(50.dp),
                        painter = painterResource(
                            id = when (state.order?.paymentMethod) {
                                PaymentMethod.KaspiQR -> R.drawable.ic_kaspi_qr
                                PaymentMethod.Card -> R.drawable.ic_card
                                PaymentMethod.Cash -> R.drawable.ic_cash
                                else -> R.drawable.ic_cash
                            }
                        ),
                        contentDescription = "payment_method"
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = state.order?.paymentMethod?.name ?: "Undefined",
                        fontSize = 13.sp,
                        color = Color(0xFF151522)
                    )
                    Spacer(modifier = Modifier.fillMaxWidth(0.6f))
                    state.order?.kaspiQrUrl?.let {
                        TextButton(
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color.Red,
                                backgroundColor = Color.Transparent
                            ),
                            onClick = {
                                coroutineScope.launch {
                                    bottomSheetState.show()
                                }
                            }
                        ) {
                            Text(text = stringResource(id = R.string.show_qr))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.order_is),
                        fontSize = 13.sp,
                        color = Color(0xFF151522)
                    )
                    Text(
                        text = "${state.order?.price ?: "Undefined "}${Currency.KZT.currencySymbol}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight(300),
                        color = Color(0xFF151522)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.delivery),
                        fontSize = 13.sp,
                        color = Color(0xFF151522)
                    )
                    Text(
                        text = "0.00${Currency.KZT.currencySymbol}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight(300),
                        color = Color(0xFF151522)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.total_price),
                        fontSize = 13.sp,
                        color = Color(0xFF151522)
                    )
                    Text(
                        text = "${state.order?.price ?: "Undefined "}${Currency.KZT.currencySymbol}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight(300),
                        color = Color(0xFF151522)
                    )
                }

                Spacer(modifier = Modifier.height(36.dp))
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color(0xFF1BD741),
                        backgroundColor = Color.Transparent
                    ),
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://wa.me/${state.order?.businessPhone?.removePrefix("+")}?text=Пишу из Hitprint. №${state.order?.number}. Здравствуйте, ${state.order?.businessName}!\n")
                        )
                        context.startActivity(intent)
                    }
                ) {
                    Image(painter = painterResource(id = R.drawable.ic_whatsapp), contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(
                            id = R.string.whatsapp_is,
                            state.order?.businessPhone.orEmpty()
                        ),
                        fontSize = 17.sp,
                        fontWeight = FontWeight(500)
                    )
                }

            }
        }
    }
}

fun getTextIdByStage(stage: OrderStage): Int {
    return when(stage){
        OrderStage.PAYMENT_DONE -> R.string.executed
        OrderStage.PRICING -> R.string.in_processing
        OrderStage.READY_FOR_PAY -> R.string.to_be_paid
        OrderStage.PAID -> R.string.delivering
        else -> R.string.cancelled
    }
}

fun getColorByStage(stage: OrderStage): Color {
    return when (stage) {
        OrderStage.PAYMENT_DONE -> Color(0xFF4CAF50)
        OrderStage.PRICING -> Color(0xFFFF9800)
        OrderStage.READY_FOR_PAY -> Color(0xFF8BC34A)
        OrderStage.PAID -> Color(0xFFFFC107)
        else -> Color(0xFFF44336)
    }
}

fun getIconByStage(stage: OrderStage): ImageVector {
    return when (stage) {
        OrderStage.PAYMENT_DONE -> Icons.Rounded.Done
        OrderStage.PRICING -> Icons.Rounded.AccessTime
        OrderStage.READY_FOR_PAY -> Icons.Rounded.CreditCard
        OrderStage.PAID -> Icons.Rounded.LocalShipping
        else -> throw RuntimeException("no stage icon~")
    }
}