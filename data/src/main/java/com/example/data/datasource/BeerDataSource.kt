package com.example.data.datasource

import com.example.domain.model.Beer
import com.example.domain.model.BeerDetail
import kotlinx.coroutines.flow.Flow

interface BeerDataSource {
    suspend fun getBeerList(beerName: String, page: Int): Flow<List<Beer>>
    suspend fun getBeerDetail(id: String): Flow<BeerDetail>
}