package com.example.feature.main.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.BeerDetail
import com.example.domain.usecase.GetBeerDetailUseCase
import com.example.domain.usecase.UpdateBookmarkBeerUseCase
import com.example.feature.main.navigator.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getBeerDetailUseCase: GetBeerDetailUseCase,
    private val updateBookmarkBeerUseCase: UpdateBookmarkBeerUseCase,
) : ViewModel() {

    init {
        getBeerDetail()
    }

    private val _error = MutableSharedFlow<Throwable>()
    val error = _error.asSharedFlow()

    private val _beerDetail = MutableStateFlow(BeerDetail())
    val beerDetail = _beerDetail.asStateFlow()

    private fun getBeerDetail() {
        viewModelScope.launch {
            val id = savedStateHandle.get<String>(Route.id) ?: return@launch
            getBeerDetailUseCase(id)
                .catch {
                    _error.emit(it)
                }
                .collect {
                _beerDetail.value = it
            }
        }
    }

    fun updateBookmark() {
        viewModelScope.launch {
            val id = _beerDetail.value.id
            val isBookmark = _beerDetail.value.isBookmark.not()
            _beerDetail.value = _beerDetail.value.copy(isBookmark = isBookmark)
            updateBookmarkBeerUseCase(id, isBookmark)
        }
    }

}