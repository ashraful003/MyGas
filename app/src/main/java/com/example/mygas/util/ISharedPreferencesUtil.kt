package com.example.mygas.util

interface ISharedPreferencesUtil {
    fun logout()
    fun getAuthToken(): String?
    fun setAuthToken(token: String)
}