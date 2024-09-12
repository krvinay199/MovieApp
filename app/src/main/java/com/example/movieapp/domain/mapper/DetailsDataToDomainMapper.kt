package com.example.movieapp.domain.mapper

import com.example.movieapp.data.model.Details.MovieDetailResponse
import com.example.movieapp.data.model.Details.Rating
import com.example.movieapp.domain.model.Details
import com.example.movieapp.domain.model.Ratings

fun MovieDetailResponse.toDomainModel(): Details {
    return Details(
        name = this.Title,
        type = this.Type,
        runtime = this.Runtime,
        actors = this.Actors,
        awards = this.Awards,
        boxOffice = this.BoxOffice,
        genre = this.Genre,
        plot = this.Plot,
        rated = this.Rated,
        year = this.Year,
        production = this.Production,
        ratings = this.Ratings?.let { getRatings(it) },
        imdbID = this.imdbID,
        imdbRating = this.imdbRating,
        imdbVotes = this.imdbVotes,
        poster = this.Poster,
        website = this.Website,
        writer = this.Writer,
        DVD = this.DVD,
        language = this.Language,
        director = this.Director,
        metasSore = this.Metascore,
        country = this.Country,
        released = this.Released,
        response = this.Response
    )
}

fun getRatings(ratings: List<Rating>): List<Ratings> {
    return ratings.map {
        Ratings(
            Source = it.Source,
            Value = it.Value
        )
    }
}
