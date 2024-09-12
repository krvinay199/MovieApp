package com.example.movieapp.presentation.view.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.movieapp.common.Resource
import com.example.movieapp.data.repository.MovieRepositoryImpl
import com.example.movieapp.data.service.RetrofitClient.API_KEY
import com.example.movieapp.data.service.RetrofitClient.retrofit
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.usecase.MovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MovieSearchViewModel(private val useCase: MovieUseCase) : ViewModel() {

    // ViewModelFactory needs to be created for the viewModel to initialize
    companion object {
        val factory = viewModelFactory {
            initializer {
                MovieSearchViewModel(
                    useCase = MovieUseCase(repository = MovieRepositoryImpl(retrofit))
                )
            }
        }
    }

    private val _movieSearchList = MutableStateFlow<MovieSearchState>(MovieSearchState.Loading)
    val movieSearchList: StateFlow<MovieSearchState> = _movieSearchList


    init {
        fetchMovies("")
    }

    fun fetchMovies(s: String) {
        useCase.invoke(query = s, apiKey = API_KEY).onEach {
            when (it) {
                is Resource.Loading -> {
                    _movieSearchList.value = MovieSearchState.Loading
                }

                is Resource.Success -> {
                    _movieSearchList.value = MovieSearchState.Success(
                        data = it.data!!
                    )

                }

                is Resource.Error -> {
                    _movieSearchList.value = MovieSearchState.Error("Unknown Error!!")
                }
            }
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }
}

sealed interface MovieSearchState {
    data object Loading : MovieSearchState
    data class Success(val data: List<Movie>) : MovieSearchState
    data class Error(val error: String) : MovieSearchState
}