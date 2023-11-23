package com.silentbit.thingshubmobile.dagger

import android.content.Context
import com.silentbit.thingshubmobile.data.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun providerDatastore(@ApplicationContext context: Context) = DataStoreManager(context)

}