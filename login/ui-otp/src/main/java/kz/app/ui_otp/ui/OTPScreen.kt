package kz.app.ui_otp.ui

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import kz.app.components.DefaultScreen
import kz.app.components.SystemBroadcastReceiver
import kz.app.ui_otp.R
import kz.app.ui_otp.ui.components.OtpView


@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun OTPScreen(
    state: OtpScreenState,
    events: (OtpScreenEvent) -> Unit,
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            val code = result.data?.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
            code?.let {
                events.invoke(OtpScreenEvent.AutoReadSmsPasscode(context as Activity, code))
            }
        }
    )
    SystemBroadcastReceiver(
        systemAction = SmsRetriever.SMS_RETRIEVED_ACTION,
        permission = SmsRetriever.SEND_PERMISSION,
        onSystemEvent = { intent ->
            val consentIntent: Intent? =
                intent?.getParcelableExtra(SmsRetriever.EXTRA_CONSENT_INTENT)
            if (consentIntent != null) launcher.launch(intent)

            if (SmsRetriever.SMS_RETRIEVED_ACTION != intent?.action) return@SystemBroadcastReceiver

            val extras = intent.extras
            val status: Status? = extras?.get(SmsRetriever.EXTRA_STATUS) as Status?
            when (status?.statusCode) {
                CommonStatusCodes.SUCCESS -> {       // Get SMS message contents
                    val message: String? = extras?.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String?
                    val reg = "[0-9]{6}".toRegex()
                    message?.let {
                        val result = reg.find(it) ?: return@SystemBroadcastReceiver
                        events.invoke(OtpScreenEvent.AutoReadSmsPasscode(context as Activity, result.value))
                    }
                }
                CommonStatusCodes.TIMEOUT -> {}
            }
        }
    )
    DefaultScreen(
        queue = state.errorQueue,
        progressBarState = state.progressBarState,
        toolbarText = stringResource(id = R.string.otp),
        navigationAction = {
            events.invoke(OtpScreenEvent.OnBackPressed)
        },
        onRemoveHeadFromQueue = {
            events.invoke(OtpScreenEvent.OnRemoveHeadFromQueue)
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
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                modifier = Modifier.padding(horizontal = 48.dp),
                text = stringResource(id = R.string.enter_otp),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
            OtpView(
                code = state.code,
                readOnly = state.retry,
                onCodeChange = { events.invoke(OtpScreenEvent.UpdateCode(it)) },
                onFilled = { otpCode ->
                    focusManager.clearFocus()
                    events.invoke(OtpScreenEvent.OnOtpCodeFilled(context as Activity, otpCode))
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            if (state.errorMessage != null) {
                Text(text = state.errorMessage.orEmpty(), color = if (state.retry) Color.Red else Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (state.retry) {
                TextButton(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = Color.Red
                    ),
                    onClick = { events.invoke(OtpScreenEvent.OnBackPressed) }
                ) {
                    Text(text = stringResource(id = R.string.try_again))
                }
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}