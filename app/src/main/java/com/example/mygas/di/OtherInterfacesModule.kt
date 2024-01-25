package com.gas.mygasbd.di

import com.gas.mygasbd.util.ISharedPreferencesUtil
import com.gas.mygasbd.util.SharePreferencesUtil
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface OtherInterfacesModule {

    @Binds
    fun bindSharePreferencesUtil(sharePreferencesUtil: SharePreferencesUtil): ISharedPreferencesUtil
}