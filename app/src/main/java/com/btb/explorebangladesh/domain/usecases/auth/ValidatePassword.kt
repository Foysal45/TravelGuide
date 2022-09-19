package com.btb.explorebangladesh.domain.usecases.auth

import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.ValidationResult
import com.btb.explorebangladesh.UiText

class ValidatePassword {
    operator fun invoke(password: String): ValidationResult {
        if (password.isEmpty() || password.length < 6) {
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