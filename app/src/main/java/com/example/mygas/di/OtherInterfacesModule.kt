package com.example.mygas.di

import com.example.mygas.util.ISharedPreferencesUtil
import com.example.mygas.util.SharePreferencesUtil
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