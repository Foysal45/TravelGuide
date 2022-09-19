package com.btb.explorebangladesh.responses

import android.util.Log
import com.btb.explorebangladesh.body.getError
import com.btb.explorebangladesh.utilities.BuildConfig
import com.btb.explorebangladesh.style.R
import com.btb.explorebangladesh.UiText
import com.btb.explorebangladesh.responses.error.ApiError
import com.btb.explorebangladesh.responses.error.toUiText
import retrofit2.Response


sealed class ApiResponse<T> {

    companion object {
        const val UNKNOWN_ERROR_CODE = 444
        const val EMPTY_ERROR_CODE = 2004

        fun <T> create(error: UiText, code: Int = 444): ApiEmptyResponse<T> {
            return ApiEmptyResponse(error, code)
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (BuildConfig.DEBUG) {
                    Log.e("ApiResponse", "create: $body")
                }
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse(
                        UiText.StringResource(R.string.message_unknown_error),
                        response.code()
                    )
                } else {
                    ApiSuccessResponse(
                        body = body
                    )
                }
            } else {
                val code = response.code()
                val error = response.errorBody().getError<ApiError>()
                Log.e("ApiResponse", "create: $error")
                if (error != null) {
                    ApiErrorResponse(error.toUiText(code), code)
                } else {
                    ApiEmptyResponse(
                        UiText.StringResource(R.string.message_unknown_error),
                        UNKNOWN_ERROR_CODE
                    )
                }
            }
        }
    }

}


class ApiEmptyResponse<T>(val text: UiText, val code: Int) : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

data class ApiErrorResponse<T>(val text: UiText, val code: Int) : ApiResponse<T>()