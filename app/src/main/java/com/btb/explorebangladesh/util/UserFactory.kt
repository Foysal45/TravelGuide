package com.btb.explorebangladesh.util

import com.google.gson.Gson
import com.btb.explorebangladesh.data.remote.request.LoginRequest
import com.btb.explorebangladesh.SharedPref

class UserFactory(
    private val sharedPref: SharedPref,
    private val gson: Gson
) {

    companion object {

        private const val PREF_ACCESS_TOKEN = "pref_access_token"
        private const val PREF_LOGIN_REQUEST = "pref_login_request"

    }

    fun getAccessToken(): String = sharedPref.read(PREF_ACCESS_TOKEN, "")

    fun saveAccessToken(token: String) {
        sharedPref.write(PREF_ACCESS_TOKEN, token)
    }

    fun getLoginRequest(): LoginRequest? {
        val requestText = sharedPref.read(PREF_LOGIN_REQUEST, "")
        return if (requestText.isNotEmpty()) {
            gson.fromJson(requestText, LoginRequest::class.java)
        } else {
            null
        }
    }

    fun saveLoginRequest(loginRequest: LoginRequest) {
        sharedPref.write(PREF_LOGIN_REQUEST, gson.toJson(loginRequest))
    }


    fun clear(){
        sharedPref.clear()
    }

}