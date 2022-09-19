package com.btb.explorebangladesh.domain.usecases.auth

import android.util.Patterns
import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.ValidationResult
import com.btb.explorebangladesh.UiText

class ValidateEmail {
    operator fun invoke(email: String): ValidationResult {
        if (email.isEmpty()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(
                    R.string.email_cant_blank
                )
            )
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(
                    R.string.invalid_email_address
                )
            )
        }

        return ValidationResult(
            isSuccessful = true
        )
    }
}