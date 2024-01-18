package com.example.domain.usecase

import com.example.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkBeerUseCase @Inject constructor(
    private val repository: BeerRepository,
) {
    suspend operator fun invoke(): Flow<Set<String>> {
        return repository.getBookmarkBeer()
    }
}