package com.gas.mygasbd.util

interface ISharedPreferencesUtil {
    fun logout()
    fun getAuthToken(): String?
    fun setAuthToken(token: String)
}