package com.example.movieapp.domain.usecase

import com.example.movieapp.common.Resource
import com.example.movieapp.data.model.MovieSearch.Search
import com.example.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieUseCase(private val repository: MovieRepository) {

    operator fun invoke(query: String, apiKey: String ): Flow<Resource<List<Search>>> = flow {
        try {
            emit(Resource.Loading())

            val result = repository.searchMovies(query, apiKey)
            if (result.Search.isNullOrEmpty().not()){
                emit(Resource.Success(data = result.Search) )
            } else {
                emit(Resource.Success(data = emptyList()) )
            }


        } catch (e: Exception){
            emit(Resource.Error("Unknown Error Occurred!!!"))
        }
    }
}