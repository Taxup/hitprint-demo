package kz.app.ui_rating

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.app.components.DefaultScreen
import kz.app.components.rating_bar.RatingBar
import kz.app.components.rating_bar.RatingBarConfig
import kz.app.components.rating_bar.RatingBarStyle

@ExperimentalComposeUiApi
@androidx.compose.runtime.Composable
fun RatingScreen(
    state: RatingState,
    events: (RatingEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    DefaultScreen(
        queue = state.queue,
        progressBarState = state.progressBarState,
        navigationAction = {
            events.invoke(RatingEvent.OnBackPressed)
        },
        onRemoveHeadFromQueue = {
            events.invoke(RatingEvent.OnRemoveHeadFromQueue)
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(12.dp), contentAlignment = Alignment.Center) {
            Card(
                elevation = 12.dp,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = stringResource(id = R.string.quality),
                            fontSize = 16.sp,
                            color = Color(0xFF666666)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        RatingBar(
                            value = state.rating.toFloat(),
                            config = RatingBarConfig()
                                .style(RatingBarStyle.HighLighted)
                                .numStars(5)
                                .size(24.dp)
                                .padding(4.dp),
                            onValueChange = { 
                                events.invoke(RatingEvent.UpdateRating(it.toInt()))
                            },
                            onRatingChanged = {
                                Log.d("TAG", "onRatingChanged: $it")
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        value = state.comment,
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color(0xFF19924F),
                            unfocusedIndicatorColor = Color.LightGray
                        ),
                        label = {
                            Text(text = stringResource(id = R.string.leave_a_comment))
                        },
                        onValueChange = {
                            events.invoke(RatingEvent.UpdateComment(it))
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(100.dp),
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = Color(0xFF5DB075),
                            contentColor = Color.White
                        ),
                        onClick = {
                            events.invoke(RatingEvent.RateCenter)
                        }
                    ) {
                        Text(text = stringResource(id = R.string.rate))
                    }
                }
            }
        }
    }
}