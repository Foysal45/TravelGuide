package com.btb.explorebangladesh.data.remote.api

import com.btb.explorebangladesh.data.model.ForgotPasswordOtpDto
import com.btb.explorebangladesh.data.model.PayloadDto
import com.btb.explorebangladesh.data.model.UserDto
import com.btb.explorebangladesh.data.remote.request.LoginRequest
import com.btb.explorebangladesh.data.remote.request.RegistrationRequest
import com.btb.explorebangladesh.data.remote.request.SocialLoginRequest
import com.btb.explorebangladesh.data.remote.responses.RegistrationData
import com.btb.explorebangladesh.responses.ResponseDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AuthApiService {

    @POST("auth/local/signin")
    suspend fun doSignIn(
        @Body body: LoginRequest
    ): Response<ResponseDTO<PayloadDto>>

    @POST("auth/local/signin")
    fun refreshToken(
        @Body body: LoginRequest
    ): Call<ResponseDTO<PayloadDto>>

    @POST("user/registration")
    suspend fun doRegistration(
        @Body body: RegistrationRequest
    ): Response<ResponseDTO<RegistrationData>>

    @POST("user/registration-padding-otp")
    suspend fun verifyRegistration(
        @Body body: Map<String, String>
    ): Response<ResponseDTO<RegistrationData>>

    @POST("auth/local/reset-pwd-req")
    suspend fun resetPasswordRequest(
        @Body body: Map<String, String>
    ): Response<ResponseDTO<ForgotPasswordOtpDto>>

    @PUT("auth/local/reset-pwd-req/{token}")
    suspend fun resetPassword(
        @Path("token") token: String,
        @Body body: Map<String, String>
    ): Response<ResponseDTO<UserDto>>

    @POST("auth/{socialType}/client-id")
    suspend fun doSocialSignIn(
        @Path("socialType") socialType: String,
        @Body body: SocialLoginRequest
    ): Response<ResponseDTO<PayloadDto>>


}