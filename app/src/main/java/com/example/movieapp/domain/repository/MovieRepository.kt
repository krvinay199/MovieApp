package com.example.movieapp.domain.repository

import com.example.movieapp.data.model.Search.MovieSearchResponse

interface MovieRepository {
    suspend fun searchMovies(query: String, apiKey: String): MovieSearchResponse
}