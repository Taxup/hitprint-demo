package kz.app.ui_order_history.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.LocalShipping
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kz.app.hitprint_domain.OrderStage
import kz.app.ui_order_history.R


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