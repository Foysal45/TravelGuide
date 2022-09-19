package com.btb.explorebangladesh.presentation.fragments.auth.register

sealed class RegistrationState {

    object RegistrationFormState: RegistrationState()
    object OtpVerificationState: RegistrationState()

}