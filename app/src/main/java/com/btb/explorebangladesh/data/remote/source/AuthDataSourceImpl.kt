package com.btb.explorebangladesh.data.remote.source


import com.btb.explorebangladesh.data.remote.api.AuthApiService
import com.btb.explorebangladesh.data.remote.request.LoginRequest
import com.btb.explorebangladesh.data.remote.request.RegistrationRequest
import com.btb.explorebangladesh.data.remote.request.SocialLoginRequest
import com.btb.explorebangladesh.middleware.SafeApiRequest


class AuthDataSourceImpl(
    private val apiService: AuthApiService
) : SafeApiRequest(), AuthDataSource {

    override suspend fun doSignIn(body: LoginRequest) = apiRequest {
        apiService.doSignIn(body)
    }

    override fun refreshToken(body: LoginRequest) = apiService.refreshToken(body)

    override suspend fun doRegistration(body: RegistrationRequest) = apiRequest {
        apiService.doRegistration(body)
    }

    override suspend fun verifyRegistration(body: Map<String, String>) = apiRequest {
        apiService.verifyRegistration(body)
    }

    override suspend fun resetPasswordRequest(body: Map<String, String>) = apiRequest {
        apiService.resetPasswordRequest(body)
    }

    override suspend fun resetPassword(
        token: String,
        body: Map<String, String>
    ) = apiRequest {
        apiService.resetPassword(token, body)
    }

    override suspend fun doSocialSignIn(
        body: SocialLoginRequest
    ) = apiRequest {
        apiService.doSocialSignIn(body.socialType, body)
    }
}