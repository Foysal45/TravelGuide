package com.btb.explorebangladesh.presentation.fragments.auth.register


sealed class RegisterFormEvent {
    data class NameChanged(val name: String) : RegisterFormEvent()
    data class EmailChanged(val email: String) : RegisterFormEvent()
    data class PhoneChanged(val phone: String) : RegisterFormEvent()
    data class CountryCode(val code: String) : RegisterFormEvent()
    data class CountryChanged(val country: String) : RegisterFormEvent()
    data class PasswordChanged(val password: String) : RegisterFormEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegisterFormEvent()
    data class OtpChanged(val otp: String): RegisterFormEvent()
    object SignUp: RegisterFormEvent()
    object Submit : RegisterFormEvent()
}