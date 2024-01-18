package com.example.domain.usecase

import com.example.domain.model.Beer
import com.example.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBeerUseCase @Inject constructor(
    private val repository: BeerRepository,
) {

    suspend operator fun invoke(beerName: String, page:Int): Flow<List<Beer>> {
        return repository.getBeerList(beerName, page)
    }

}