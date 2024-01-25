package com.gas.mygas.di

import android.app.Activity
import com.gas.mygasbd.util.MGActivityUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object MyGasActivityModule {

    @Provides
    fun providedMGActivityUtil(activity: Activity): MGActivityUtil {
        return MGActivityUtil(activity as MGActivityUtil.ActivityListener)
    }
}