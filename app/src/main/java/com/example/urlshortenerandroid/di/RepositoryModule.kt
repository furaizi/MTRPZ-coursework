package com.example.urlshortenerandroid.di

import com.example.urlshortenerandroid.data.remote.LinkApi
import com.example.urlshortenerandroid.data.repository.LinkRepository
import com.example.urlshortenerandroid.domain.usecase.CreateLinkUC
import com.example.urlshortenerandroid.domain.usecase.DeleteLinkUC
import com.example.urlshortenerandroid.domain.usecase.GetLinkDetailsUC
import com.example.urlshortenerandroid.domain.usecase.GetLinkStatsUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    // -------- Repository --------
    @Provides
    @Singleton
    fun provideLinkRepository(api: LinkApi) = LinkRepository(api)

    // -------- Use-cases --------
    @Provides
    fun provideCreateLinkUC(repo: LinkRepository) = CreateLinkUC(repo)

    @Provides
    fun provideGetDetailsUC(repo: LinkRepository) = GetLinkDetailsUC(repo)

    @Provides
    fun provideGetStatsUC(repo: LinkRepository) = GetLinkStatsUC(repo)

    @Provides
    fun provideDeleteLinkUC(repo: LinkRepository) = DeleteLinkUC(repo)

}