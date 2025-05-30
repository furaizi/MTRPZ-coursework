package com.example.urlshortenerandroid.di

import com.example.urlshortenerandroid.data.repository.InMemoryLinkRepository
import com.example.urlshortenerandroid.data.repository.LinkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DebugRepositoryModule {

    @Provides
    @Singleton
    fun bindInMemoryRepo(): LinkRepository = InMemoryLinkRepository()
}