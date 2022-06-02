package kz.app.ui_login.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.firebase.FirebaseApiNotAvailableException
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import kz.app.core.domain.Logger
import kz.app.core.domain.ProgressBarState
import kz.app.core.domain.Queue
import kz.app.core.domain.UIComponent
import kz.app.firebase_auth.FirebaseAuthenticator
import kz.app.navigation.Navigator
import kz.app.navigation.Screen
import kz.app.ui_login.R
import kz.app.utils.PhoneDataStore
import kz.app.utils.showAlert
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class LoginViewModel @Inject constructor(
    @Named("login-logger") private val logger: Logger,
    private val dataStore: PhoneDataStore,
     @ApplicationContext private val context: Context,
    private val navigator: Navigator
) : ViewModel() {

    val state: MutableState<LoginScreenState> = mutableStateOf(LoginScreenState())

    init {
        viewModelScope.launch {
            dataStore.getPhone.collect {
                onTriggerEvent(LoginScreenEvent.UpdatePhoneNumber(it))
            }
        }
    }

    fun onTriggerEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.UpdatePhoneNumber -> updatePhoneNumber(event.phoneNumber)
            is LoginScreenEvent.OnNumberError -> onNumberError(event.message)
            LoginScreenEvent.OnRemoveHeadFromQueue -> removeHeadMessage()
            is LoginScreenEvent.OnNextClicked -> onNextClicked(event.activity, event.phoneNumber)
            LoginScreenEvent.OnBackPressed -> navigator.navigateUp()
        }
    }

    private fun onNextClicked(activity: Activity, phoneNumber: String) {
        if (phoneNumber.isValid()) {
            /* Start reading One Time Password from sms */
            val client = SmsRetriever.getClient(activity)
            val task = client.startSmsRetriever()
            client.startSmsUserConsent(null)

            task.addOnSuccessListener {
                sendSms(activity, phoneNumber)
            }

            task.addOnFailureListener {
                activity.showAlert(it.message.orEmpty())
            }
        }
    }

    private fun sendSms(activity: Activity, phoneNumber: String) {
        state.value = state.value.copy(progressBarState = ProgressBarState.Loading)
        FirebaseAuthenticator.sendSms(
            activity = activity,
            mobileNum = phoneNumber,
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onCodeAutoRetrievalTimeOut(verificationId: String) {
                    super.onCodeAutoRetrievalTimeOut(verificationId)
                    FirebaseAuthenticator.setTimeoutCallback(Exception(context.getString(R.string.error_sms_timeout)))
                    state.value = state.value.copy(progressBarState = ProgressBarState.Idle)
                }

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                        createUser(phoneNumber)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    val description = when(e) {
                        is FirebaseAuthInvalidCredentialsException -> context.getString(R.string.error_invalid_phone)
                        is FirebaseAuthException -> context.getString(R.string.error_app_not_authorized)
                        is FirebaseTooManyRequestsException -> context.getString(R.string.error_sms_exhausted)
                        is FirebaseApiNotAvailableException -> context.getString(R.string.error_no_google_services)
                        else -> context.getString(R.string.error_unknown)
                    }
                    appendToMessageQueue(
                        UIComponent.Dialog(description = description)
                    )
                    state.value = state.value.copy(progressBarState = ProgressBarState.Idle)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    super.onCodeSent(verificationId, token)
                    viewModelScope.launch {
                        dataStore.savePhone(phoneNumber)
                        navigator.navigateTo(Screen.OTP, verificationId)
                    }
                    state.value = state.value.copy(progressBarState = ProgressBarState.Idle)
                }
            }
        )
    }

    private fun String.isValid(): Boolean {
        if (this.length < 10) {
            state.value = state.value.copy(phoneValidationError = context.getString(R.string.enter_full_number))
            return false
        }
        if (this.substring(0..2) !in kzNumbers) {
            state.value = state.value.copy(phoneValidationError = context.getString(R.string.enter_kz_number))
            return false
        }
        return true
    }


    private fun onNumberError(message: String) {
        appendToMessageQueue(
            UIComponent.Dialog(description = message)
        )
        state.value = state.value.copy(progressBarState = ProgressBarState.Idle)
    }

    private fun updatePhoneNumber(phoneNumber: String) {
        state.value = state.value.copy(phoneNumber = phoneNumber, phoneValidationError = null)
    }

    private fun appendToMessageQueue(uiComponent: UIComponent) {
        val queue = state.value.errorQueue
        queue.add(uiComponent)
        state.value = state.value.copy(errorQueue = Queue())
        state.value = state.value.copy(errorQueue = queue)
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

    companion object {
        private val kzNumbers = listOf("700", "701", "702", "705", "706", "707", "708", "747", "771", "775", "776", "777", "778")
    }

}