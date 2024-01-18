package com.example.data.di

import com.example.data.datasource.BeerDataSource
import com.example.data.datasource.BookmarkBeerDataSource
import com.example.data.datasource.DefaultBeerDataSource
import com.example.data.datasource.DefaultBookmarkBeerDataSource
import com.example.data.repository.DefaultBeerRepository
import com.example.domain.repository.BeerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsBeerDataSource(
        defaultBeerDataSource: DefaultBeerDataSource,
    ): BeerDataSource

    @Binds
    abstract fun bindsBookmarkBeerDataSource(
        defaultBookmarkBeerDataSource: DefaultBookmarkBeerDataSource,
    ): BookmarkBeerDataSource

    @Binds
    abstract fun bindsBeerRepository(
        repository: DefaultBeerRepository,
    ): BeerRepository

}
