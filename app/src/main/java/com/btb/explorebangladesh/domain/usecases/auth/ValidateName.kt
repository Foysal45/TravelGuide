package com.btb.explorebangladesh.domain.usecases.auth

import com.btb.explorebangladesh.UiText
import com.btb.explorebangladesh.ValidationResult
import com.btb.explorebangladesh.R

class ValidateName {
    operator fun invoke(name: String): ValidationResult {
        if (name.isEmpty()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(
                    R.string.name_must_be_non_empty
                )
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}