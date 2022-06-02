package kz.app.ui_login.ui

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.app.components.DefaultScreen
import kz.app.ui_login.R

@ExperimentalAnimationApi
@Composable
fun LoginScreen(
    state: LoginScreenState,
    events: (LoginScreenEvent) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    DefaultScreen(
        queue = state.errorQueue,
        progressBarState = state.progressBarState,
        navigationAction = {
            events.invoke(LoginScreenEvent.OnBackPressed)
        },
        onRemoveHeadFromQueue = {
            events.invoke(LoginScreenEvent.OnRemoveHeadFromQueue)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(147.dp, 138.dp),
                painter = painterResource(id = R.drawable.logok),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                modifier = Modifier.padding(horizontal = 48.dp),
                text = stringResource(id = R.string.welcome),
                fontSize = 32.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                modifier = Modifier.padding(horizontal = 48.dp),
                text = stringResource(id = R.string.enter_phone_number),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                modifier = Modifier.padding(horizontal = 24.dp),
                value = state.phoneNumber,
                onValueChange = {
                    if (it.length <= 10) events.invoke(LoginScreenEvent.UpdatePhoneNumber(it))
                },
                isError = state.errorQueue.isNotEmpty() || state.phoneValidationError != null,
                label = {
                    Text(text = state.phoneValidationError ?: stringResource(id = R.string.enter_phone_number))
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF5DB075),
                    focusedLabelColor = Color(0xFF5DB075)
                ),
                visualTransformation = MaskTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.clearFocus()
                        events.invoke(LoginScreenEvent.OnNextClicked(context as Activity, state.phoneNumber))
                    }
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            focusManager.clearFocus()
                            events.invoke(LoginScreenEvent.OnNextClicked(context as Activity, state.phoneNumber))
                        }
                    ) {
                        Image(
                            Icons.Filled.ArrowForward,
                            contentDescription = ""
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}