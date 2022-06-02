package kz.app.firebase_auth

import android.app.Activity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

object FirebaseAuthenticator {

    private val mAuth by lazy { FirebaseAuth.getInstance() }

    private var timeoutCallback: OnFailureListener? = null

    fun setTimeoutCallback(timeoutError: Exception) {
        timeoutCallback?.onFailure(timeoutError)
    }

    fun sendSms(
        activity: Activity,
        mobileNum: String,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber("+7$mobileNum")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifySms(
        activity: Activity,
        otp: String,
        verificationId: String,
        listener: OnCompleteListener<AuthResult>,
        failureListener: OnFailureListener
    ) {
        val credential = PhoneAuthProvider.getCredential(verificationId, otp)
        FirebaseAuth.getInstance()
            .signInWithCredential(credential)
            .addOnCompleteListener(activity, listener)
            .addOnFailureListener(failureListener)
        timeoutCallback = failureListener
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

}
