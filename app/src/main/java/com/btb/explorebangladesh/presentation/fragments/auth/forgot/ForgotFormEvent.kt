package com.btb.explorebangladesh.presentation.fragments.auth.forgot


sealed class ForgotFormEvent {

    data class EmailChanged(val email: String) : ForgotFormEvent()
    data class OtpChanged(val otpCode: String) : ForgotFormEvent()
    data class PasswordChanged(val password: String) : ForgotFormEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : ForgotFormEvent()
    object ContinueWithEmail : ForgotFormEvent()
    object ContinueWithOtp : ForgotFormEvent()
    object ResetPassword : ForgotFormEvent()

}