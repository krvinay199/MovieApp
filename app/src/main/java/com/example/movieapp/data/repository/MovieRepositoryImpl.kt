package com.example.movieapp.data.repository

import com.example.movieapp.data.model.Search.MovieSearchResponse
import com.example.movieapp.data.service.MovieSearchAPI
import com.example.movieapp.domain.repository.MovieRepository

class MovieRepositoryImpl (private val api: MovieSearchAPI): MovieRepository {
    override suspend fun searchMovies(query: String, apiKey: String): MovieSearchResponse {
        return api.searchMovies(query, apiKey)
    }
}