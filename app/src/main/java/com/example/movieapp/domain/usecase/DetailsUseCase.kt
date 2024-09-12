package com.example.movieapp.domain.usecase

import com.example.movieapp.common.Resource
import com.example.movieapp.domain.mapper.toDomainModel
import com.example.movieapp.domain.model.Details
import com.example.movieapp.domain.repository.DetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailsUseCase(private val detailsRepository: DetailsRepository) {

    operator fun invoke(imdbId: String, apiKey: String): Flow<Resource<Details>> = flow {

        try {
            emit(Resource.Loading())

            val result = detailsRepository.getMovieDetails(imdbId, apiKey).toDomainModel()

            emit(Resource.Success(result))
        } catch (_: Exception) {
            emit(Resource.Error("Unknown Error Occurred!!!"))
        }
    }
}