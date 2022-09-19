package com.btb.explorebangladesh.data.remote.source

import com.btb.explorebangladesh.data.model.ForgotPasswordOtpDto
import com.btb.explorebangladesh.data.model.PayloadDto
import com.btb.explorebangladesh.data.model.UserDto
import com.btb.explorebangladesh.data.remote.request.LoginRequest
import com.btb.explorebangladesh.data.remote.request.RegistrationRequest
import com.btb.explorebangladesh.data.remote.request.SocialLoginRequest
import com.btb.explorebangladesh.data.remote.responses.RegistrationData
import com.btb.explorebangladesh.responses.ApiResponse
import com.btb.explorebangladesh.responses.ResponseDTO
import retrofit2.Call

interface AuthDataSource {

    suspend fun doSignIn(
        body: LoginRequest
    ): ApiResponse<ResponseDTO<PayloadDto>>

    fun refreshToken(
        body: LoginRequest
    ): Call<ResponseDTO<PayloadDto>>

    suspend fun doRegistration(
        body: RegistrationRequest
    ): ApiResponse<ResponseDTO<RegistrationData>>

    suspend fun verifyRegistration(
        body: Map<String, String>
    ): ApiResponse<ResponseDTO<RegistrationData>>

    suspend fun resetPasswordRequest(
        body: Map<String, String>
    ): ApiResponse<ResponseDTO<ForgotPasswordOtpDto>>


    suspend fun resetPassword(
        token: String,
        body: Map<String, String>
    ): ApiResponse<ResponseDTO<UserDto>>

    suspend fun doSocialSignIn(
        body: SocialLoginRequest
    ): ApiResponse<ResponseDTO<PayloadDto>>

}