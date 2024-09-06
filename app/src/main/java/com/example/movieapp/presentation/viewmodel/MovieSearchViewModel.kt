package com.example.movieapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.common.Resource
import com.example.movieapp.data.model.MovieSearch.Search
import com.example.movieapp.domain.usecase.MovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MovieSearchViewModel(private val useCase: MovieUseCase) : ViewModel() {

    // ViewModelFactory needs to be created for the viewModel to initialize

    private val _movieSearchList = MutableStateFlow<MovieSearchState>(MovieSearchState.Loading)
    val movieSearchList: StateFlow<MovieSearchState> = _movieSearchList


    init {
        fetchMovies("")
    }

    fun fetchMovies(s: String) {
        useCase.invoke(query = s, apiKey = "3afb443e").onEach {
            when (it) {
                is Resource.Loading -> {
                    _movieSearchList.value = MovieSearchState.Loading
                }

                is Resource.Error -> {
                    _movieSearchList.value = MovieSearchState.Error("Unknown Error!!")
                }

                is Resource.Success -> {
                    _movieSearchList.value = MovieSearchState.Success(
                        data = it.data ?: emptyList()
                    )

                }
            }
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }
}

sealed interface MovieSearchState {
    data object Loading : MovieSearchState
    data class Success(val data: List<Search>) : MovieSearchState
    data class Error(val error: String) : MovieSearchState
}