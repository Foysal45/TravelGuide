package com.btb.explorebangladesh.presentation.fragments.auth.forgot

sealed class ForgotState {
    object EmailState : ForgotState()
    object OtpState : ForgotState()
    object ResetState : ForgotState()
}
