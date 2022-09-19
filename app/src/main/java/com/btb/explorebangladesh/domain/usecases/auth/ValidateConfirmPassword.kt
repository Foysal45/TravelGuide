package com.btb.explorebangladesh.domain.usecases.auth

import com.btb.explorebangladesh.UiText
import com.btb.explorebangladesh.ValidationResult
import com.btb.explorebangladesh.R

class ValidateConfirmPassword {
    operator fun invoke(password: String, confirmPassword: String): ValidationResult {
        if (password != confirmPassword) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(
                    R.string.password_length_must_6
                )
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }

}