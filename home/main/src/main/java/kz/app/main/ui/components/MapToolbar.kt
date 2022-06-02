package kz.app.main.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.app.components.R

@Composable
fun MapToolbar(
    address: String?,
    onLogout: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp)
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(
                imageVector = Icons.Rounded.Home,
                contentDescription = "home",
                tint = Color(0xFF199450)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = address ?: stringResource(id = R.string.not_selected_yet),
                fontSize = 16.sp,
                fontWeight = FontWeight(300)
            )
        }

        Image(
            painter = painterResource(id = kz.app.navigation.R.drawable.ic_exit),
            contentDescription = stringResource(id = R.string.logout),
            modifier = Modifier.clickable { onLogout.invoke() }
        )
    }
}