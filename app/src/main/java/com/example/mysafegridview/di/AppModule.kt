package com.example.mysafegridview.di

import com.example.mysafegridview.data.repository.ShopRepositoryImpl
import com.example.mysafegridview.presentation.ShopRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideShopRepository() : ShopRepository = ShopRepositoryImpl()
}