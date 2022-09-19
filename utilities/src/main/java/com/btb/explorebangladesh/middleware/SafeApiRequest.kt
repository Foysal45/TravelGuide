package com.btb.explorebangladesh.middleware

import com.btb.explorebangladesh.style.R
import com.btb.explorebangladesh.UiText
import com.btb.explorebangladesh.responses.ApiResponse
import org.json.JSONException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

abstract class SafeApiRequest {
    suspend fun <S : Any> apiRequest(call: suspend () -> Response<S>): ApiResponse<S> {
        return try {
            val response = call.invoke()
            ApiResponse.create(response)
        } catch (e: JSONException) {
            ApiResponse.create(
                UiText.StringResource(
                R.string.message_json_error
            ))
        } catch (e: IOException) {
            ApiResponse.create(
                UiText.StringResource(
                R.string.message_network_error
            ))
        } catch (e: SocketTimeoutException) {
            ApiResponse.create(
                UiText.StringResource(
                R.string.message_network_error
            ))
        } catch (e: Exception) {
            ApiResponse.create(
                UiText.StringResource(
                R.string.message_unknown_error
            ))
        }

    }

}