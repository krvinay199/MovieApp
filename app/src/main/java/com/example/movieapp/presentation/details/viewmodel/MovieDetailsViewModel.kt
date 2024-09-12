package com.example.movieapp.presentation.view.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.movieapp.common.Resource
import com.example.movieapp.data.repository.DetailsRepositoryImpl
import com.example.movieapp.data.service.RetrofitClient.API_KEY
import com.example.movieapp.data.service.RetrofitClient.retrofit
import com.example.movieapp.domain.usecase.DetailsUseCase
import com.example.movieapp.presentation.view.details.uiState.DetailsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MovieDetailsViewModel(private val useCase: DetailsUseCase) : ViewModel() {

    companion object {
        val factory = viewModelFactory {
            initializer {
                MovieDetailsViewModel(
                    useCase = DetailsUseCase(detailsRepository = DetailsRepositoryImpl(retrofit))
                )
            }
        }
    }

    private val _movieDetails = MutableStateFlow<DetailsState>(DetailsState(isLoading = true))
    val movieDetails: StateFlow<DetailsState> = _movieDetails


    init {
        fetchMovieDetails("")
    }

    fun fetchMovieDetails(id: String) {
        useCase.invoke(imdbId = id, apiKey = API_KEY).onEach {
            when (it) {
                is Resource.Loading -> {
                    _movieDetails.value = DetailsState(isLoading = true)
                }

                is Resource.Success -> {
                    _movieDetails.value = DetailsState(
                        data = it.data
                    )

                }

                is Resource.Error -> {
                    _movieDetails.value = DetailsState(error = "Unknown Error!!")
                }
            }
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }
}