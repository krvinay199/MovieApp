package com.example.movieapp.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.model.MovieSearch.Search
import com.example.movieapp.databinding.ItemViewBinding
import com.example.movieapp.domain.model.Movie

class MovieAdapter(private var movies: List<Search>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.titleTextView.text = movie.Title
        holder.yearTextView.text = movie.Year
    }

    override fun getItemCount() = movies.size

    class MovieViewHolder(binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val titleTextView = binding.title
        val yearTextView = binding.year
    }

    fun setMovieList(list: MutableList<Search>){
        this.movies = list
        notifyDataSetChanged()
    }

    // We'll have to write itemClickListener
}
