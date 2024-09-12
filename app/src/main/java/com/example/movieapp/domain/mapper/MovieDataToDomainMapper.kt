package com.example.movieapp.domain.mapper

import com.example.movieapp.data.model.Search.Search
import com.example.movieapp.domain.model.Movie

fun Search.toDomainModel(): Movie {
    return Movie(
        title = this.Title,
        poster = this.Poster,
        year = this.Year,
        type = this.Type,
        imdb = this.imdbID
    )
}