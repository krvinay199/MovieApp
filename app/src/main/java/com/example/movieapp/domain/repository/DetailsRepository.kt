package com.example.movieapp.domain.repository

import com.example.movieapp.data.model.Details.MovieDetailResponse

interface DetailsRepository {
    suspend fun getMovieDetails(imdbId: String, apiKey: String): MovieDetailResponse
}