package com.btb.explorebangladesh.domain.usecases.auth

import com.btb.explorebangladesh.UiText
import com.btb.explorebangladesh.ValidationResult
import com.btb.explorebangladesh.R

class ValidateOtp {

    operator fun invoke(otp: String, serverOtp: String): ValidationResult {
        if (otp.isEmpty()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(
                    R.string.otp_cant_blank
                )
            )
        }

        if (otp.length < 6) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(
                    R.string.otp_must_be_6_characters_length
                )
            )
        }
        if(otp != serverOtp){
            return ValidationResult(
                isSuccessful = false,
                errorMessage = UiText.StringResource(
                    R.string.otp_does_not_match
                )
            )
        }

        return ValidationResult(
            isSuccessful = true
        )
    }
}