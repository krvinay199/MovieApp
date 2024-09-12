package com.example.movieapp.presentation.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentDetailsBinding
import com.example.movieapp.presentation.view.details.viewmodel.MovieDetailsViewModel
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private lateinit var _binding: FragmentDetailsBinding
    val binding: FragmentDetailsBinding
        get() = _binding

    private val viewModel: MovieDetailsViewModel by viewModels { MovieDetailsViewModel.factory }

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchMovieDetails(args.imdbId ?: "")

        lifecycle.coroutineScope.launch {
            viewModel.movieDetails.collect {
                if (it.isLoading) {
                    binding.progressMealSearch.visibility = View.VISIBLE
                }
                if (it.error.isNotBlank()) {
                    binding.progressMealSearch.visibility = View.GONE
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                }
                it.data?.let {
                    binding.progressMealSearch.visibility = View.GONE
                    binding.movieDetails = it
                    //Movie Poster
                    context?.let { it1 ->
                        Glide.with(it1)
                            .load(it.poster)
                            .apply(RequestOptions()).placeholder(R.drawable.ic_loading)
                            .into(binding.moviePoster)
                    }
                }
            }
        }

        binding.detailsBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}