package kz.app.ui_order_history.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.app.core.domain.Currency
import kz.app.hitprint_domain.PayOrder
import kz.app.ui_order_history.R
import kz.app.ui_order_history.ui.utils.getColorByStage
import kz.app.ui_order_history.ui.utils.getIconByStage
import kz.app.ui_order_history.ui.utils.getTextIdByStage

@ExperimentalMaterialApi
@Composable
fun OrderHistoryItem(order: PayOrder, onItemClicked: (orderNumber: String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .clickable {
                onItemClicked.invoke(order.number)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(width = 44.dp, height = 44.dp)
                .clip(CircleShape)
                .background(getColorByStage(order.stage), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = getIconByStage(order.stage),
                tint = Color.White,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = "${stringResource(id = R.string.order_is)} ${order.number}", color = Color.Black, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(id = R.string.center_is, order.businessName), color = Color.LightGray, fontSize = 14.sp)
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = stringResource(id = getTextIdByStage(order.stage)),
                color = getColorByStage(order.stage),
                fontSize = 12.sp
            )
            order.price?.let {
                Text(
                    text = "${order.price}${Currency.KZT.currencySymbol}",
                    color = Color.Black,
                    fontSize = 12.sp
                )
            }
        }
    }
    Divider()
}