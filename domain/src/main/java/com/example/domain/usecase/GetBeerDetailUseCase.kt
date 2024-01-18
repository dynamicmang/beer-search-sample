package com.example.domain.usecase

import com.example.domain.model.BeerDetail
import com.example.domain.repository.BeerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBeerDetailUseCase @Inject constructor(
    private val repository: BeerRepository,
) {

    suspend operator fun invoke(id: String): Flow<BeerDetail> {
        return repository.getBeerDetail(id)
    }

}