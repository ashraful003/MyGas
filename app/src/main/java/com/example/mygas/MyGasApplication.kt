package com.example.mygas

import android.app.Application
import android.content.Context

class MyGasApplication:Application() {
    companion object{
        fun getApplication(context: Context) = context.applicationContext as MyGasApplication
    }
}