package kz.app.ui_otp.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kz.app.core.domain.*
import kz.app.firebase_auth.FirebaseAuthenticator
import kz.app.hitprint_interactors.CreateUser
import kz.app.navigation.Navigator
import kz.app.navigation.Screen
import kz.app.ui_otp.R
import kz.app.utils.PhoneDataStore
import javax.inject.Inject
import javax.inject.Named
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class OtpViewModel @Inject constructor(
    @Named("otp-logger") private val logger: Logger,
    private val createUser: CreateUser,
    private val dataStore: PhoneDataStore,
    private val navigator: Navigator,
    @ApplicationContext private val context: Context,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    val state: MutableState<OtpScreenState> = mutableStateOf(OtpScreenState())

    init {
        tickerFlow(60.seconds)
            .onEach { seconds ->
                state.value = state.value.copy(errorMessage = context.getString(R.string.sms_time_remaining, seconds))
            }
            .onCompletion {
                state.value = state.value.copy(retry = true, errorMessage = context.getString(R.string.error_sms_timeout))
            }
            .launchIn(viewModelScope)
    }

    private fun tickerFlow(duration: Duration, initialDelay: Duration = Duration.ZERO) = flow {
        delay(initialDelay)
        var seconds = duration.inWholeSeconds
        while (seconds != 0L) {
            emit(seconds--)
            delay(1.seconds)
        }
    }

    fun onTriggerEvent(event: OtpScreenEvent) {
        when (event) {
            OtpScreenEvent.OnRemoveHeadFromQueue -> removeHeadMessage()
            is OtpScreenEvent.OnOtpCodeFilled -> onOtpCodeFilled(event.activity, event.code)
            is OtpScreenEvent.OnOtpEnteredWrong -> onOtpEnteredWrong(event.message)
            OtpScreenEvent.OnBackPressed -> navigator.navigateUp()
            is OtpScreenEvent.AutoReadSmsPasscode -> autoReadSms(event.activity, event.code)
            is OtpScreenEvent.UpdateCode -> updateCode(event.code)
        }
    }

    private fun autoReadSms(activity: Activity, code: String) {
        if (code.length == 6) {
            state.value = state.value.copy(code = code)
            onOtpCodeFilled(activity, code)
        }
    }

    private fun updateCode(code: String) {
        state.value = state.value.copy(code = code)
    }

    private fun onOtpEnteredWrong(message: String) {
        appendToMessageQueue(
            UIComponent.Dialog(
                title = "Attention!",
                description = message
            )
        )
    }

    private fun onOtpCodeFilled(activity: Activity, code: String) {
        state.value = state.value.copy(progressBarState = ProgressBarState.Loading)
        viewModelScope.launch {
            val phoneNumber: String = dataStore.getPhone.first()
            FirebaseAuthenticator.verifySms(
                activity = activity,
                otp = code,
                verificationId = savedStateHandle.get<String>("verificationId").orEmpty(),
                listener = { task ->
                    if (task.isSuccessful) {
                        FirebaseMessaging.getInstance().token.addOnCompleteListener{ tokenResult ->
                            if (!tokenResult.isSuccessful) {
                                Log.w(
                                    this::class.java.name,
                                    "Fetching FCM registration token failed",
                                    tokenResult.exception
                                )
                                Toast.makeText(activity, "Fetching FCM registration token failed", Toast.LENGTH_LONG).show()
                            }
                            // Get new FCM registration token
                            val token = tokenResult.result
                            createUser(phoneNumber, token)
                        }
                    }
                },
                failureListener = {
                    state.value = state.value.copy(
                        progressBarState = ProgressBarState.Idle,
                        retry = true,
                        errorMessage = "Failed to verify phone number. \n${it.message}"
                    )
                    appendToMessageQueue(
                        UIComponent.Dialog(
                            title = "Fail!",
                            description = "Failed to verify phone number. \n${it.message}"
                        )
                    )
                }
            )
        }
    }

    private fun createUser(phoneNumber: String, token: String) {
        createUser.execute(phoneNumber, token).onEach { dataState ->
            when (dataState) {
                is DataState.Data -> {
                    if (dataState.data == true) {
                        dataStore.saveSession(phoneNumber)
                        navigator.navigateTo(Screen.UserAddress, popUpTo = Screen.Splash, inclusive = true)
                    }
                }
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> appendToMessageQueue(dataState.uiComponent)
                        is UIComponent.Default -> logger.log((dataState.uiComponent as UIComponent.Default).message)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.value.errorQueue
            queue.remove()
            state.value = state.value.copy(errorQueue = Queue())
            state.value = state.value.copy(errorQueue = queue)
        } catch (e: Exception) {
            logger.log(e.message.toString())
        }
    }


    private fun appendToMessageQueue(uiComponent: UIComponent) {
        val queue = state.value.errorQueue
        queue.add(uiComponent)
        state.value = state.value.copy(errorQueue = Queue())
        state.value = state.value.copy(errorQueue = queue)
    }


}