package kz.app.ui_wait.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.app.components.DefaultScreen
import kz.app.ui_wait.R
import java.util.*

@Composable
fun OrderCreateSuccessScreen(
    state: OrderCreateSuccessState,
    events: (OrderCreateSuccessEvent) -> Unit
) {
   DefaultScreen(
       queue = state.queue,
       navigationIcon = null,
       onRemoveHeadFromQueue = { events.invoke(OrderCreateSuccessEvent.OnRemoveHeadFromQueue) },
       floatingActionButton = {
           ExtendedFloatingActionButton(
               modifier = Modifier.fillMaxWidth(.7f),
               backgroundColor = Color(0xFF199450),
               contentColor = Color.White,
               elevation = FloatingActionButtonDefaults.elevation(
                   defaultElevation = 0.dp,
                   pressedElevation = 4.dp,
                   hoveredElevation = 0.dp,
                   focusedElevation = 0.dp,
               ),
               text = {
                   Text(text = stringResource(id = R.string.go_main).uppercase(Locale.getDefault()))
               },
               onClick = { events.invoke(OrderCreateSuccessEvent.GoMain) }
           )
       }
   ) {
       Column(
           modifier = Modifier.fillMaxSize(),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center
       ) {
           Image(
               modifier = Modifier.size(150.dp, 150.dp),
               painter = painterResource(id = R.drawable.ic_checked_success),
               contentDescription = null
           )
           Spacer(modifier = Modifier.height(40.dp))
           Text(
               modifier = Modifier.padding(horizontal = 24.dp),
               textAlign = TextAlign.Center,
               text = stringResource(id = R.string.application_create_success_title),
               fontWeight = FontWeight.Bold,
               fontSize = 16.sp,
               color = Color.Black
           )
           Spacer(modifier = Modifier.height(8.dp))
           Text(
               modifier = Modifier.padding(horizontal = 24.dp),
               textAlign = TextAlign.Center,
               text = stringResource(id = R.string.application_create_success_description),
               fontSize = 14.sp,
               color = Color.Gray
           )
           Spacer(modifier = Modifier.height(100.dp))

//           LoadingAnimation(
//                circleColor = Color(0xFF22BA68),
//                circleBottomColor = Color(0x0022BA68)
//           )
       }
   }
}