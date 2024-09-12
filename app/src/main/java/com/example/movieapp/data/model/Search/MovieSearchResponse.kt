package com.example.movieapp.data.model.Search

data class MovieSearchResponse(
    val Response: String?,
    val Search: List<Search>,
    val totalResults: String?
)