package com.example.movieapp.data.service

import com.example.movieapp.data.model.MovieSearch.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieSearchAPI {
    @GET("/")
    suspend fun searchMovies(@Query("s") query: String, @Query("apiKey") apiKey: String): MovieSearchResponse

}