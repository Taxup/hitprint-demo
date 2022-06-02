package kz.app.ui_login.ui

import android.app.Activity

sealed class LoginScreenEvent {

    class UpdatePhoneNumber(val phoneNumber: String) : LoginScreenEvent()

    class OnNumberError(val message: String) : LoginScreenEvent()

    object OnRemoveHeadFromQueue : LoginScreenEvent()

    class OnNextClicked(val activity: Activity, val phoneNumber: String) : LoginScreenEvent()

    object OnBackPressed : LoginScreenEvent()
}
