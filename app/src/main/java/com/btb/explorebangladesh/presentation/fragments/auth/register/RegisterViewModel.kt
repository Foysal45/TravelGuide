package com.btb.explorebangladesh.presentation.fragments.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.btb.explorebangladesh.Resource
import com.btb.explorebangladesh.ValidationEvent
import com.btb.explorebangladesh.data.remote.request.RegistrationRequest
import com.btb.explorebangladesh.domain.repository.AuthRepository
import com.btb.explorebangladesh.domain.usecases.auth.*
import com.btb.explorebangladesh.presentation.fragments.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateName: ValidateName,
    private val validateEmail: ValidateEmail,
    private val validateCountryName: ValidateCountryName,
    private val validatePassword: ValidatePassword,
    private val validateConfirmPassword: ValidateConfirmPassword,
    private val validateOtp: ValidateOtp,
    private val repository: AuthRepository
) : BaseViewModel() {

    private val name = MutableLiveData<String>()
    private val email = MutableLiveData<String>()
    private val phone = MutableLiveData<String>()
    private val countryCode = MutableLiveData<String>()

    private val countryName = MutableLiveData<String>()

    private val password = MutableLiveData<String>()
    private val confirmPassword = MutableLiveData<String>()

    private val otpCode = MutableLiveData<String>()

    private var token = ""
    private var serverOtp = ""


    private val _registrationState =
        MutableLiveData<RegistrationState>(RegistrationState.RegistrationFormState)
    val registrationState: LiveData<RegistrationState>
        get() = _registrationState

    private val _validationEvent = MutableLiveData<ValidationEvent>()
    val validationEvent: LiveData<ValidationEvent>
        get() = _validationEvent

    fun onRegisterEvent(event: RegisterFormEvent) {
        when (event) {
            is RegisterFormEvent.NameChanged -> {
                name.value = event.name
            }
            is RegisterFormEvent.EmailChanged -> {
                email.value = event.email
            }
            is RegisterFormEvent.PhoneChanged -> {
                phone.value = event.phone
            }
            is RegisterFormEvent.CountryCode -> {
                countryCode.value = event.code
            }
            is RegisterFormEvent.CountryChanged -> {
                countryName.value = event.country
            }
            is RegisterFormEvent.PasswordChanged -> {
                password.value = event.password
            }
            is RegisterFormEvent.ConfirmPasswordChanged -> {
                confirmPassword.value = event.confirmPassword
            }
            is RegisterFormEvent.OtpChanged -> {
                otpCode.value = event.otp
            }
            RegisterFormEvent.SignUp -> {
                doRegister()
            }
            RegisterFormEvent.Submit -> {
                verifyRegistration()
            }

        }
    }

    private fun doRegister() {
        val name = name.value ?: ""
        val email = email.value ?: ""
        val phone = countryCode.value ?: ""+phone.value ?: ""
        val countryName = countryName.value ?: ""
        val password = password.value ?: ""
        val confirmPassword = confirmPassword.value ?: ""

        val nameResult = validateName(name)
        val emailResult = validateEmail(email)
        val countryNameResult = validateCountryName(countryName)
        val passwordResult = validatePassword(password)
        val confirmPasswordResult = validateConfirmPassword(
            password,
            confirmPassword
        )

        if (!nameResult.isSuccessful) {
            _error.postValue(nameResult.errorMessage)
            _fieldName.postValue("name")
            return
        }
        if (!emailResult.isSuccessful) {
            _error.postValue(emailResult.errorMessage)
            _fieldName.postValue("email")
            return
        }
        if (!countryNameResult.isSuccessful) {
            _error.postValue(countryNameResult.errorMessage)
            _fieldName.postValue("country")
            return
        }
        if (!passwordResult.isSuccessful) {
            _error.postValue(passwordResult.errorMessage)
            _fieldName.postValue("password")
            return
        }

        if (!confirmPasswordResult.isSuccessful) {
            _error.postValue(confirmPasswordResult.errorMessage)
            _fieldName.postValue("name")
            return
        }
        val registrationRequest = RegistrationRequest(
            fullName = name,
            phoneNumber = phone,
            country = countryName,
            email = email,
            password = password
        )

        viewModelScope.launch {
            when (val resource = repository.doRegistration(registrationRequest)) {
                is Resource.Failure -> {
                    _validationEvent.postValue(
                        ValidationEvent.Failure(
                            resource.text,
                            resource.code
                        )
                    )
                }
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    token = resource.data.registrationToken
                    serverOtp = resource.data.otp
                    otpCode.postValue(serverOtp)
                    _registrationState.postValue(RegistrationState.OtpVerificationState)
                }
            }
        }


    }

    private fun verifyRegistration() {
        val otp = otpCode.value ?: ""
        val otpResult = validateOtp(otp, serverOtp)
        if (!otpResult.isSuccessful) {
            _error.postValue(otpResult.errorMessage)
            return
        }

        viewModelScope.launch {
            when (val resource = repository.verifyRegistration(
                mapOf(
                    "registrationToken" to token,
                    "otp" to otp
                )
            )) {
                is Resource.Failure -> {
                    _isLoading.postValue(false)
                    _validationEvent.postValue(
                        ValidationEvent.Failure(
                            resource.text,
                            resource.code
                        )
                    )
                }
                is Resource.Loading -> _isLoading.postValue(true)
                is Resource.Success -> {
                    _isLoading.postValue(false)
                    _validationEvent.postValue(ValidationEvent.Success)
                }
            }
        }

    }

}