package com.example.data.datasource

import kotlinx.coroutines.flow.Flow

interface BookmarkBeerDataSource {
    suspend fun getBookmarkBeer(): Flow<Set<String>>
    suspend fun updateBookmarkBeer(bookmarkBeer: Set<String>)
}