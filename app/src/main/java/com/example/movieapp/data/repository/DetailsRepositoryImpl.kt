package com.example.movieapp.data.repository

import com.example.movieapp.data.model.Details.MovieDetailResponse
import com.example.movieapp.data.service.MovieSearchAPI
import com.example.movieapp.domain.repository.DetailsRepository

class DetailsRepositoryImpl(val api: MovieSearchAPI) : DetailsRepository {
    override suspend fun getMovieDetails(imdbId: String, apiKey: String): MovieDetailResponse {
        return api.fetchDetails(imdbId = imdbId, apiKey = apiKey)
    }
}