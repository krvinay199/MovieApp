package com.example.movieapp.domain.model

data class Details(
    val name: String?,
    val type: String?,
    val runtime: String?,
    val actors: String?,
    val awards: String?,
    val boxOffice: String?,
    val country: String?,
    val DVD: String?,
    val director: String?,
    val genre: String?,
    val language: String?,
    val metasSore: String?,
    val plot: String?,
    val poster: String?,
    val production: String?,
    val rated: String?,
    val ratings: List<Ratings?>?,
    val released: String?,
    val response: String?,
    val website: String?,
    val writer: String?,
    val year: String?,
    val imdbID: String?,
    val imdbRating: String?,
    val imdbVotes: String?
)

data class Ratings(
    val Source: String?,
    val Value: String?
)
