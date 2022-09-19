package com.btb.explorebangladesh.domain.usecases.auth

import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.SocialType
import com.btb.explorebangladesh.UiText
import com.btb.explorebangladesh.ValidationResult

class ValidateSocialType {
    operator fun invoke(type: String): ValidationResult {
        if (type.isEmpty()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(
                    R.string.social_type_must_not_be_empty
                )
            )
        }

        if (SocialType.isNotValidSocialType(type)) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(
                    R.string.social_type_not_valid
                )
            )
        }

        return ValidationResult(
            isSuccessful = true
        )
    }
}