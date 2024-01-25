package com.example.mygas

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyGasApplication : Application() {
    companion object {
        @JvmStatic
        fun getApplication(context: Context) = context.applicationContext as MyGasApplication
    }
}