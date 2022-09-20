package com.btb.explorebangladesh.data.repository

import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.data.mapper.toPayload
import com.btb.explorebangladesh.data.remote.source.AuthDataSource
import com.btb.explorebangladesh.domain.repository.AuthRepository
import com.btb.explorebangladesh.Resource
import com.btb.explorebangladesh.UiText
import com.btb.explorebangladesh.data.mapper.toOtp
import com.btb.explorebangladesh.data.mapper.toRegistration
import com.btb.explorebangladesh.data.mapper.toUser
import com.btb.explorebangladesh.data.remote.request.LoginRequest
import com.btb.explorebangladesh.data.remote.request.RegistrationRequest
import com.btb.explorebangladesh.data.remote.request.SocialLoginRequest
import com.btb.explorebangladesh.responses.ApiEmptyResponse
import com.btb.explorebangladesh.responses.ApiErrorResponse
import com.btb.explorebangladesh.responses.ApiResponse.Companion.UNKNOWN_ERROR_CODE
import com.btb.explorebangladesh.responses.ApiSuccessResponse

class AuthRepositoryImpl(
    private val dataSource: AuthDataSource
) : AuthRepository {

    override suspend fun doSignIn(
        body: LoginRequest
    ) = when (val response = dataSource.doSignIn(body)) {
        is ApiEmptyResponse -> Resource.Failure(response.text, response.code)
        is ApiErrorResponse -> Resource.Failure(response.text, response.code)
        is ApiSuccessResponse -> {
            val dto = response.body.dto
            if (dto != null) {
                Resource.Success(dto.toPayload())
            } else {
                Resource.Failure(
                    UiText.StringResource(
                        R.string.message_unknown_error
                    ),
                    UNKNOWN_ERROR_CODE
                )
            }
        }
    }

    override fun refreshToken(body: LoginRequest) = dataSource.refreshToken(body)

    override suspend fun doRegistration(
        body: RegistrationRequest
    ) = when (val response = dataSource.doRegistration(body)) {
        is ApiEmptyResponse -> Resource.Failure(response.text, response.code)
        is ApiErrorResponse -> Resource.Failure(response.text, response.code)
        is ApiSuccessResponse -> {
            val dto = response.body.dto
            if (dto != null) {
                Resource.Success(dto.registration.toRegistration())
            } else {
                Resource.Failure(
                    UiText.StringResource(
                        R.string.message_unknown_error
                    ),
                    UNKNOWN_ERROR_CODE
                )
            }
        }
    }

    override suspend fun verifyRegistration(
        body: Map<String, String>
    ) = when (val response = dataSource.verifyRegistration(body)) {
        is ApiEmptyResponse -> Resource.Failure(response.text, response.code)
        is ApiErrorResponse -> Resource.Failure(response.text, response.code)
        is ApiSuccessResponse -> {
            val dto = response.body.dto
            if (dto != null) {
                Resource.Success(dto.registration.toRegistration())
            } else {
                Resource.Failure(
                    UiText.StringResource(
                        R.string.message_unknown_error
                    ),
                    UNKNOWN_ERROR_CODE
                )
            }
        }
    }

    override suspend fun resetPasswordRequest(
        body: Map<String, String>
    ) = when (val response = dataSource.resetPasswordRequest(body)) {
        is ApiEmptyResponse -> Resource.Failure(response.text, response.code)
        is ApiErrorResponse -> Resource.Failure(response.text, response.code)
        is ApiSuccessResponse -> {
            val dto = response.body.dto
            if (dto != null) {
                Resource.Success(dto.toOtp())
            } else {
                Resource.Failure(
                    UiText.StringResource(
                        R.string.message_unknown_error
                    ),
                    UNKNOWN_ERROR_CODE
                )
            }
        }
    }

    override suspend fun resetPassword(
        token: String, body: Map<String, String>
    ) = when (val response = dataSource.resetPassword(token, body)) {
        is ApiEmptyResponse -> Resource.Failure(response.text, response.code)
        is ApiErrorResponse -> Resource.Failure(response.text, response.code)
        is ApiSuccessResponse -> {
            val dto = response.body.dto
            if (dto != null) {
                Resource.Success(dto.toUser())
            } else {
                Resource.Failure(
                    UiText.StringResource(
                        R.string.message_unknown_error
                    ),
                    UNKNOWN_ERROR_CODE
                )
            }
        }
    }

    override suspend fun doSocialSignIn(
        body: SocialLoginRequest
    ) = when (val response = dataSource.doSocialSignIn(body)) {
        is ApiEmptyResponse -> Resource.Failure(response.text, response.code)
        is ApiErrorResponse -> Resource.Failure(response.text, response.code)
        is ApiSuccessResponse -> {
            val dto = response.body.dto
            if (dto != null) {
                Resource.Success(dto.toPayload())
            } else {
                Resource.Failure(
                    UiText.StringResource(
                        R.string.message_unknown_error
                    ),
                    UNKNOWN_ERROR_CODE
                )
            }
        }
    }

}