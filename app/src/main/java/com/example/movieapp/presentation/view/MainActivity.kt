package com.example.movieapp.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.presentation.viewmodel.MovieSearchState
import com.example.movieapp.presentation.viewmodel.MovieSearchViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MovieSearchViewModel by viewModels()

    private val movieAdapter: MovieAdapter = MovieAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpSearchView(viewModel.movieSearchList)

    }

    private fun setUpSearchView(movieSearchList: StateFlow<MovieSearchState>) {
        lifecycle.coroutineScope.launch {
            movieSearchList.collect{
                when(it){
                    is MovieSearchState.Error -> {
                        val errorText: TextView = findViewById(R.id.error_text)
                        errorText.text = it.error
                    }
                    MovieSearchState.Loading -> {
                        val progressBar: ProgressBar = findViewById(R.id.progress_bar)
                        progressBar.apply {
                            visibility = View.VISIBLE
                        }
                    }
                    is MovieSearchState.Success -> {
                        val searchView: SearchView = findViewById(R.id.editTextSearch)
                        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewMovies)

                        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                query?.let { it1 -> viewModel.fetchMovies(it1) }
                                return false
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                return false
                            }

                        })

                        recyclerView.apply {
                            adapter = movieAdapter
                            movieAdapter.setMovieList(it.data.toMutableList())
                        }
                    }
                }
            }

        }
    }
}

