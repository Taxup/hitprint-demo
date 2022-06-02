package kz.app.ui_packages.ui

import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.app.ui_packages.R
import kz.app.components.DefaultScreen
import kz.app.core.domain.Currency
import kz.app.hitprint_domain.Package
import kz.app.hitprint_domain.PackageType
import java.util.*


@Composable
fun PackagesScreen(
    state: PackagesState,
    events: (PackagesEvent) -> Unit
) {
    val context = LocalContext.current
    DefaultScreen(
        queue = state.queue,
        progressBarState = state.progressBarState,
        toolbarText = stringResource(id = R.string.packages),
        navigationAction = {
            events.invoke(PackagesEvent.OnBackPressed)
        },
        onRemoveHeadFromQueue = {
            events.invoke(PackagesEvent.OnRemoveHeadFromQueue)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.fillMaxWidth(.7f),
                backgroundColor = Color(0xFF199450),
                contentColor = Color.White,
                text = {
                    Text(text = stringResource(id = R.string.select_package).uppercase(Locale.getDefault()))
                },
                onClick = {
                    events.invoke(PackagesEvent.OnNextClicked)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(9.dp, 14.dp)
                    .clickable {
                        val url = "https://youtu.be/rKw0D2lfyRw"
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        startActivity(context, intent, null)
                    },
                elevation = 2.dp,
                backgroundColor = Color.Black,
                shape = RoundedCornerShape(12.dp)
            ) {
                Image(
                    modifier = Modifier.padding(135.dp, 65.dp),
                    painter = painterResource(R.drawable.ic_play),
                    contentDescription = "",
                )
            }
            state.packages.groupBy { it.type }.forEach { (type, packages) ->
                Spacer(modifier = Modifier.height(22.dp))
                Text(
                    modifier = Modifier.padding(9.dp),
                    fontSize = 24.sp,
                    text = type.name.lowercase(Locale.getDefault())
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                )
                val iconRes = if (type == PackageType.STANDARD) R.drawable.standard_packages else R.drawable.eco_packages
                LazyRow {
                    items(packages) { pack ->
                        PackageItem(
                            pack = pack,
                            isSelected = pack.id == state.selectedPackageId,
                            iconRes = iconRes,
                            onClick = {
                                events.invoke(PackagesEvent.SelectPackage(pack.id))
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun PackageItem(
    pack: Package,
    isSelected: Boolean,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit
) {
    Column {
        Image(
            modifier = Modifier
                .height(110.dp)
                .width(110.dp)
                .padding(9.dp)
                .shadow(6.dp, shape = RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    color = if (isSelected) Color(0xFF4B9460) else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .selectable(
                    selected = isSelected,
                    onClick = onClick
                )
                .clip(RoundedCornerShape(8.dp)),
            painter = painterResource(id = iconRes),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(9.dp),
            fontSize = 14.sp,
            text = pack.name
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .padding(9.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.W600,
            text = "${pack.price} ${Currency.create(pack.currencyCode).currencySymbol}"
        )
    }
}