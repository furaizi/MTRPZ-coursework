package com.example.urlshortenerandroid.di

import com.example.urlshortenerandroid.BuildConfig
import com.example.urlshortenerandroid.data.remote.LinkApi
import com.example.urlshortenerandroid.data.repository.InMemoryLinkRepository
import com.example.urlshortenerandroid.data.repository.LinkRepository
import com.example.urlshortenerandroid.data.repository.LinkRepositoryImpl
import com.example.urlshortenerandroid.domain.usecase.CreateLinkUC
import com.example.urlshortenerandroid.domain.usecase.DeleteLinkUC
import com.example.urlshortenerandroid.domain.usecase.GetLinkDetailsUC
import com.example.urlshortenerandroid.domain.usecase.GetLinkStatsUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLinkRepository(api: LinkApi): LinkRepository = if (BuildConfig.DEBUG)
        InMemoryLinkRepository()
    else
        LinkRepositoryImpl(api)

}