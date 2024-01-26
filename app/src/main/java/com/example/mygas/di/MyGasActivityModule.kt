package com.example.mygas.di

import android.app.Activity
import com.example.mygas.util.MGActivityUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object MyGasActivityModule {
    @Provides
    fun providedMGBActivityUtil(activity: Activity): MGActivityUtil {
        return MGActivityUtil(activity as MGActivityUtil.ActivityListener)
    }
}