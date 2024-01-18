@file:OptIn(FlowPreview::class)

package com.example.feature.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Beer
import com.example.domain.usecase.GetBeerUseCase
import com.example.domain.usecase.GetBookmarkBeerUseCase
import com.example.feature.main.stae.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBeerUseCase: GetBeerUseCase,
    private val getBookmarkBeerUseCase: GetBookmarkBeerUseCase,
) : ViewModel() {

    private val _error = MutableSharedFlow<Throwable>()
    val error = _error.asSharedFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val debouncedSearchQuery: Flow<String> = searchQuery.debounce(700)

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.None)
    val searchUiState = _searchUiState.asStateFlow()

    private var totalPage = 0
    private var currentPage = 1

    init {
        viewModelScope.launch {
            debouncedSearchQuery
                .filter { it.isNotEmpty() }
                .collect {
                    refreshSearchResult()
                }
        }
        viewModelScope.launch {
            getBookmarkBeerUseCase()
                .collect { bookmarkBeerSet ->
                    if (searchUiState.value is SearchUiState.Result) {
                        _searchUiState.value = (searchUiState.value as SearchUiState.Result).copy(
                            beerList = (searchUiState.value as SearchUiState.Result).beerList.map { beer ->
                                beer.copy(isBookmark = bookmarkBeerSet.contains(beer.id))
                            }
                        )
                    }
                }
        }
    }

    fun updateSearchQuery(searchQuery: String) {
        _searchQuery.value = searchQuery
    }

    private fun refreshSearchResult() {
        viewModelScope.launch {
            totalPage = 0
            currentPage = 1
            _searchUiState.value = SearchUiState.Loading
            getBeerUseCase(searchQuery.value, currentPage++)
                .catch {
                    _searchUiState.value = SearchUiState.None
                    _error.emit(it)
                }
                .collect {
                    _searchUiState.value = if (it.isEmpty()) {
                        totalPage = currentPage
                        SearchUiState.Empty
                    } else {
                        if (it.size < 20) totalPage = currentPage
                        SearchUiState.Result(beerList = it)
                    }
                }
        }
    }

    fun loadMoreSearchResult() {
        viewModelScope.launch {
            if (totalPage == currentPage) return@launch
            getBeerUseCase(searchQuery.value, currentPage++)
                .catch {
                    _searchUiState.value = SearchUiState.None
                    _error.emit(it)
                }
                .collect {
                    if (it.isEmpty() || it.size < 20) totalPage = currentPage
                    if (searchUiState.value is SearchUiState.Result) {
                        _searchUiState.value = SearchUiState.Result(
                            beerList = arrayListOf<Beer>().apply {
                                addAll((searchUiState.value as SearchUiState.Result).beerList)
                                addAll(it)
                            }
                        )
                    }
                }
        }
    }

}
