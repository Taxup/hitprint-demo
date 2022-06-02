package kz.app.ui_otp.ui

import android.app.Activity

sealed class OtpScreenEvent {

    class OnOtpCodeFilled(val activity: Activity, val code: String) : OtpScreenEvent()

    object OnRemoveHeadFromQueue : OtpScreenEvent()

    object OnBackPressed : OtpScreenEvent()

    class OnOtpEnteredWrong(val message: String): OtpScreenEvent()

    data class UpdateCode(val code: String) : OtpScreenEvent()

    data class AutoReadSmsPasscode(val activity: Activity, val code: String) : OtpScreenEvent()
}
