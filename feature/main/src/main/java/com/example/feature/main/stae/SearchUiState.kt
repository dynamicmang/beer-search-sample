package com.example.feature.main.stae

import com.example.domain.model.Beer

sealed interface SearchUiState {
    object None : SearchUiState
    object Loading : SearchUiState
    object Empty : SearchUiState
    data class Result(
        val beerList: List<Beer>,
    ) : SearchUiState
}