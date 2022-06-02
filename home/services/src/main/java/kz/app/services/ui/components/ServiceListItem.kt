package kz.app.services.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import kz.app.core.domain.Currency
import kz.app.hitprint_domain.PrintService
import kz.app.services.R

@Composable
fun ServiceListItem(
    service: PrintService,
    imageLoader: ImageLoader,
    isSelected: Boolean,
    onSelectService: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .background(MaterialTheme.colors.surface)
            .clickable {
                onSelectService(service.id)
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter = rememberImagePainter(
                data = service.imageSrc,
                imageLoader = imageLoader,
                builder = {
                    placeholder(
                        if (isSystemInDarkTheme()) R.drawable.black_background
                        else R.drawable.white_background
                    )
                }
            )
            Image(
                modifier = Modifier
                    .width(120.dp)
                    .height(90.dp)
                    .border(width = 0.5.dp, color = Color.LightGray)
                    .background(Color.LightGray),
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(.75f) // fill 80% of remaining width
                    .padding(start = 12.dp),
                text = service.name,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 12.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${service.price} ${Currency.create(service.currencyCode).currencySymbol}",
                    style = MaterialTheme.typography.caption,
                    color = Color(0xFF009a34),
                )
            }
        }
        Divider(thickness = 2.dp, color = if (isSelected) Color(0xFF009a34) else MaterialTheme.colors.onSurface.copy(alpha = 0.12f))
    }
}