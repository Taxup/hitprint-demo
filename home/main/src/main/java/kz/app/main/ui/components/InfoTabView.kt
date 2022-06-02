package kz.app.main.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import kz.app.hitprint_domain.Center
import kz.app.main.R
import java.util.*


@Composable
fun InfoTabView(
    center: Center,
    onCenterSelected: (Center) -> Unit,
    onLinkCLiked: (String) -> Unit
) {
    Column {
        CenterInfoItem(
            title = stringResource(id = R.string.work_time),
            imageVector = Icons.Rounded.CalendarToday,
            description = center.workTime
        )
        CenterInfoItem(
            title = stringResource(id = R.string.city),
            imageVector = Icons.Rounded.Apartment,
            description = center.city
        )
        CenterInfoItem(
            title = stringResource(id = R.string.address),
            imageVector = Icons.Rounded.Dns,
            description = center.address
        )
        center.website?.let{ website ->
            CenterInfoItem(
                title = stringResource(id = R.string.website),
                description = website,
                imageVector = Icons.Rounded.Public,
                descriptionStyle = LocalTextStyle.current.copy(
                    color = Color(0xFF0074cc),
                    textDecoration = TextDecoration.Underline
                ),
                onClick = {
                    onLinkCLiked.invoke(website)
                }
            )
        }
        center.description?.let{ description ->
            CenterInfoItem(
                title = stringResource(id = R.string.description),
                description = description,
                imageVector = Icons.Rounded.Info,
            )
        }


        Spacer(modifier = Modifier.height(24.dp))
        TextButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color(0xFF35A166)
            ),
            onClick = { onCenterSelected.invoke(center) },
        ) {
            Text(text = stringResource(id = R.string.shared_select).uppercase(Locale.getDefault()))
        }
        Spacer(modifier = Modifier.height(24.dp))

    }
}
