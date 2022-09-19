package com.btb.explorebangladesh.domain.usecases.auth

import com.btb.explorebangladesh.UiText
import com.btb.explorebangladesh.ValidationResult
import com.btb.explorebangladesh.R

class ValidateCountryName {
    operator fun invoke(countryName: String): ValidationResult {
        if (countryName.isEmpty()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(
                    R.string.country_name_must_be_non_empty
                )
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}