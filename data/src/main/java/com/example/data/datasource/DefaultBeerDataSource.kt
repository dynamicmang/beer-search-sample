package com.example.data.datasource

import com.example.data.api.BeerApi
import com.example.data.model.asBeerDetail
import com.example.data.model.asBeerList
import com.example.domain.model.Beer
import com.example.domain.model.BeerDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultBeerDataSource @Inject constructor(
    private val api: BeerApi,
) : BeerDataSource {

    override suspend fun getBeerList(beerName: String, page: Int): Flow<List<Beer>> {
        return flow {
            emit(api.getBeerList(beerName, page).asBeerList())
        }
    }

    override suspend fun getBeerDetail(id: String): Flow<BeerDetail> {
        return flow {
            emit(api.getBeerDetail(id)[0].asBeerDetail())
        }
    }

}