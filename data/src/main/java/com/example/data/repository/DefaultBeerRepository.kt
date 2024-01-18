package com.example.data.repository

import com.example.data.datasource.BeerDataSource
import com.example.data.datasource.BookmarkBeerDataSource
import com.example.domain.model.Beer
import com.example.domain.model.BeerDetail
import com.example.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DefaultBeerRepository @Inject constructor(
    private val beerDataSource: BeerDataSource,
    private val bookmarkBeerDataSource: BookmarkBeerDataSource,
) : BeerRepository {

    override suspend fun getBeerList(beerName: String, page: Int): Flow<List<Beer>> {
        return beerDataSource.getBeerList(beerName, page)
            .combine(bookmarkBeerDataSource.getBookmarkBeer()) { beerList, bookmarkBeerSet ->
                beerList.map { beer ->
                    beer.copy(isBookmark = bookmarkBeerSet.contains(beer.id))
                }
            }
    }

    override suspend fun getBeerDetail(id: String): Flow<BeerDetail> {
        return beerDataSource.getBeerDetail(id)
            .combine(bookmarkBeerDataSource.getBookmarkBeer()) { beerDetail, bookmarkBeerSet ->
                beerDetail.copy(isBookmark = bookmarkBeerSet.contains(beerDetail.id))
            }
    }

    override suspend fun getBookmarkBeer(): Flow<Set<String>> {
        return bookmarkBeerDataSource.getBookmarkBeer()
    }

    override suspend fun updateBookmarkBeer(id: String, isBookmark: Boolean) {
        val bookmarks = bookmarkBeerDataSource.getBookmarkBeer().first()
        bookmarkBeerDataSource.updateBookmarkBeer(
            if (isBookmark) bookmarks + id else bookmarks - id
        )
    }

}