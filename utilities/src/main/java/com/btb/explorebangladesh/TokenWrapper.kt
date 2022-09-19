package com.btb.explorebangladesh

interface TokenWrapper {
    fun getAccessToken(): String
    fun saveAccessToken(token: String)

}