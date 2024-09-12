package com.example.movieapp.presentation.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentSearchBinding
import com.example.movieapp.presentation.search.adapter.MovieAdapter
import com.example.movieapp.presentation.view.search.viewmodel.MovieSearchState
import com.example.movieapp.presentation.view.search.viewmodel.MovieSearchViewModel
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var _binding: FragmentSearchBinding
    val binding: FragmentSearchBinding
        get() = _binding

    private val searchViewModel: MovieSearchViewModel by viewModels {
        MovieSearchViewModel.factory
    }

    private val movieAdapter = MovieAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return _binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextSearchBar.setOnClickListener {
            binding.editTextSearchBar.isIconified = false
        }

        binding.editTextSearchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { it1 -> searchViewModel.fetchMovies(it1) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })


        binding.recyclerViewMovies.apply {
            adapter = movieAdapter
        }

        lifecycle.coroutineScope.launch {
            searchViewModel.movieSearchList.collect {
                when (it) {
                    MovieSearchState.Loading -> {
//                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is MovieSearchState.Success -> {
                        binding.errorText.visibility = View.GONE
//                        binding.progressBar.visibility = View.GONE

                        if (it.data.isNotEmpty()) {
                            movieAdapter.setMovieList(it.data.toMutableList())
                        } else {
                            binding.errorText.apply {
                                visibility = View.VISIBLE
                                text = getString(R.string.no_data_found)
                            }
                        }

                        movieAdapter.itemClickListener {
                            findNavController().navigate(
                                SearchFragmentDirections.actionSearchFragmentToDetailsFragment(
                                    imdbId = it.imdb
                                )
                            )
                        }
                    }

                    is MovieSearchState.Error -> {
                        /*binding.errorText.apply {
                            visibility = View.VISIBLE
                            text = getString(R.string.unknown_error)
                        }*/
                    }

                }
            }
        }
    }
}