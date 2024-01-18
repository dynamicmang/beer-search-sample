package com.example.domain.usecase

import com.example.domain.repository.BeerRepository
import javax.inject.Inject

class UpdateBookmarkBeerUseCase @Inject constructor(
    private val repository: BeerRepository,
) {
    suspend operator fun invoke(id: String, isBookmark: Boolean) {
        repository.updateBookmarkBeer(id, isBookmark)
    }
}