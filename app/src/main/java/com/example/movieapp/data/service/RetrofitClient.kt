package com.example.movieapp.data.service

import com.example.movieapp.data.model.MovieSearch.MovieSearchResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val BASE_URL = "https://www.omdbapi.com/"

    val retrofit : MovieSearchAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieSearchAPI::class.java)
    }
}