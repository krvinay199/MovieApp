package com.example.movieapp.domain.usecase

import com.example.movieapp.common.Resource
import com.example.movieapp.domain.mapper.toDomainModel
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieUseCase(private val repository: MovieRepository) {

    operator fun invoke(query: String, apiKey: String): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())

            val response = repository.searchMovies(query, apiKey).Search.map {
                it.toDomainModel()
            }
            if (response.isEmpty().not()) {
                emit(
                    Resource.Success(
                        data = response
                    )
                )
            } else {
                emit(Resource.Success(data = emptyList()))
            }


        } catch (e: Exception) {
            emit(Resource.Error("Unknown Error Occurred!!!"))
        }
    }
}