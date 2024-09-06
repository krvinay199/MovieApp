package com.example.movieapp.data.model.MovieSearch

data class MovieSearchResponse(
    val Response: String?,
    val Search: List<Search>,
    val totalResults: String?
)