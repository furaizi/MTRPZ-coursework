package com.example.urlshortenerandroid.di

import com.example.urlshortenerandroid.data.remote.LinkApi
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

    // -------- Repository --------
    @Provides
    @Singleton
    fun provideLinkRepository(api: LinkApi) = LinkRepositoryImpl(api)

    // -------- Use-cases --------
    @Provides
    fun provideCreateLinkUC(repo: LinkRepositoryImpl) = CreateLinkUC(repo)

    @Provides
    fun provideGetDetailsUC(repo: LinkRepositoryImpl) = GetLinkDetailsUC(repo)

    @Provides
    fun provideGetStatsUC(repo: LinkRepositoryImpl) = GetLinkStatsUC(repo)

    @Provides
    fun provideDeleteLinkUC(repo: LinkRepositoryImpl) = DeleteLinkUC(repo)

}