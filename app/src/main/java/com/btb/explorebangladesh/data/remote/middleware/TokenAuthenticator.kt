package com.btb.explorebangladesh.data.remote.middleware

import com.btb.explorebangladesh.domain.repository.AuthRepository
import com.btb.explorebangladesh.util.UserFactory
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val userFactory: UserFactory,
    private val authRepository: AuthRepository
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val accessToken = refreshToken()
        userFactory.saveAccessToken(accessToken)
        val token = if (accessToken.isNotEmpty()) {
            "Bearer $accessToken"
        } else {
            ""
        }
        return response.request.newBuilder()
            .header("Authorization", token)
            .build()
    }

    private fun refreshToken(): String {
        val loginRequest = userFactory.getLoginRequest()
        return if (loginRequest != null) {
            val signInResponse = authRepository.refreshToken(
                loginRequest
            ).execute().body()
            signInResponse?.dto?.accessToken ?: ""
        } else {
            ""
        }
    }
}