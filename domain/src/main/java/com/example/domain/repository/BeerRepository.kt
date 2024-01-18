package com.example.domain.repository

import com.example.domain.model.Beer
import com.example.domain.model.BeerDetail
import kotlinx.coroutines.flow.Flow

interface BeerRepository {

    suspend fun getBeerList(beerName: String, page: Int): Flow<List<Beer>>

    suspend fun getBeerDetail(id: String): Flow<BeerDetail>

    suspend fun getBookmarkBeer(): Flow<Set<String>>

    suspend fun updateBookmarkBeer(id: String, isBookmark: Boolean)

}