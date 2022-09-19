package com.btb.explorebangladesh.domain.repository

import com.btb.explorebangladesh.domain.model.Payload
import com.btb.explorebangladesh.Resource
import com.btb.explorebangladesh.data.model.PayloadDto
import com.btb.explorebangladesh.data.remote.request.LoginRequest
import com.btb.explorebangladesh.data.remote.request.RegistrationRequest
import com.btb.explorebangladesh.data.remote.request.SocialLoginRequest
import com.btb.explorebangladesh.domain.model.ForgotPasswordOtp
import com.btb.explorebangladesh.domain.model.Registration
import com.btb.explorebangladesh.domain.model.User
import com.btb.explorebangladesh.responses.ResponseDTO
import retrofit2.Call


interface AuthRepository {

    suspend fun doSignIn(
        body: LoginRequest
    ): Resource<Payload>

    fun refreshToken(
        body: LoginRequest
    ): Call<ResponseDTO<PayloadDto>>

    suspend fun doRegistration(
        body: RegistrationRequest
    ): Resource<Registration>

    suspend fun verifyRegistration(
        body: Map<String, String>
    ): Resource<Registration>

    suspend fun resetPasswordRequest(
        body: Map<String, String>
    ): Resource<ForgotPasswordOtp>


    suspend fun resetPassword(
        token: String,
        body: Map<String, String>
    ): Resource<User>

    suspend fun doSocialSignIn(
        body: SocialLoginRequest
    ): Resource<Payload>

}