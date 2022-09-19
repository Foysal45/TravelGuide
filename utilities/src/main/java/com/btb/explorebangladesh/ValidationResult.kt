package com.btb.explorebangladesh

data class ValidationResult(
    val isSuccessful: Boolean,
    val errorMessage: UiText? = null
)