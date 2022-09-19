package com.btb.explorebangladesh.presentation.fragments.auth.forgot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.Resource
import com.btb.explorebangladesh.UiText
import com.btb.explorebangladesh.domain.repository.AuthRepository
import com.btb.explorebangladesh.domain.usecases.auth.ValidateConfirmPassword
import com.btb.explorebangladesh.domain.usecases.auth.ValidateEmail
import com.btb.explorebangladesh.domain.usecases.auth.ValidateOtp
import com.btb.explorebangladesh.domain.usecases.auth.ValidatePassword
import com.btb.explorebangladesh.ValidationEvent
import com.btb.explorebangladesh.presentation.fragments.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validateOtp: ValidateOtp,
    private val validatePassword: ValidatePassword,
    private val validateConfirmPassword: ValidateConfirmPassword,
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private var token = ""
    private var serverOtp = ""

    private val _state = MutableLiveData<ForgotState>()
    val state: LiveData<ForgotState>
        get() = _state

    private val _validationEvent = MutableLiveData<ValidationEvent>()
    val validationEvent: LiveData<ValidationEvent>
        get() = _validationEvent

    private val email = MutableLiveData<String>()
    private val otpCode = MutableLiveData<String>()
    private val password = MutableLiveData<String>()
    private val confirmPassword = MutableLiveData<String>()


    fun updateState(state: ForgotState) {
        _state.postValue(state)
    }

    fun onForgotEvent(event: ForgotFormEvent) {
        when (event) {
            is ForgotFormEvent.EmailChanged -> {
                email.value = event.email
            }
            is ForgotFormEvent.PasswordChanged -> {
                password.value = event.password
            }
            is ForgotFormEvent.ConfirmPasswordChanged -> {
                confirmPassword.value = event.confirmPassword
            }

            is ForgotFormEvent.OtpChanged -> {
                otpCode.value = event.otpCode
            }

            ForgotFormEvent.ContinueWithEmail -> {
                requestForOtp()
            }
            ForgotFormEvent.ContinueWithOtp -> {
                checkOtpInput()
            }
            ForgotFormEvent.ResetPassword -> {
                resetPassword()
            }
        }
    }

    private fun requestForOtp() {
        val email = email.value ?: ""
        val emailResult = validateEmail(email = email)
        if (!emailResult.isSuccessful) {
            _error.postValue(emailResult.errorMessage)
            return
        }

        viewModelScope.launch {
            when (val resource = authRepository.resetPasswordRequest(
                mapOf(
                    "email" to email
                )
            )) {
                is Resource.Failure -> {
                    _error.postValue(resource.text)
                }
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    token = resource.data.token
                    serverOtp = resource.data.otp
                    otpCode.postValue(resource.data.otp)
                    updateState(ForgotState.OtpState)
                }
            }
        }
    }

    private fun checkOtpInput() {
        val otp = otpCode.value ?: ""
        val otpResult = validateOtp(otp, serverOtp)

        if (serverOtp.isEmpty()) {
            _error.postValue(UiText.StringResource(R.string.server_otp_not_valid))
            return
        }

        if (!otpResult.isSuccessful) {
            _error.postValue(otpResult.errorMessage)
            return
        }

        updateState(ForgotState.ResetState)
    }

    private fun resetPassword() {
        val password = password.value ?: ""
        val confirmPassword = confirmPassword.value ?: ""
        val otp = otpCode.value ?: ""
        val passwordResult = validatePassword(password)
        val confirmPasswordResult = validateConfirmPassword(password, confirmPassword)

        if (!passwordResult.isSuccessful) {
            _error.postValue(passwordResult.errorMessage)
            return
        }

        if (!confirmPasswordResult.isSuccessful) {
            _error.postValue(confirmPasswordResult.errorMessage)
            return
        }

        viewModelScope.launch {
            when (val resource = authRepository.resetPassword(
                token, mapOf(
                    "otp" to otp,
                    "password" to password
                )
            )) {
                is Resource.Failure -> {
                    _error.postValue(resource.text)
                }
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    _validationEvent.postValue(ValidationEvent.Success)
                }
            }
        }

    }

}