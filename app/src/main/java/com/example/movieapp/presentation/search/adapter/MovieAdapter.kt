package com.example.movieapp.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemViewBinding
import com.example.movieapp.domain.model.Movie

class MovieAdapter() :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var movies = mutableListOf<Movie>()

    private var listener: ((Movie) -> Unit)? = null

    fun setMovieList(list: MutableList<Movie>) {
        this.movies = list
        notifyDataSetChanged()
    }

    class MovieViewHolder(binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val titleTextView = binding.title
        val yearTextView = binding.year
        val movieImage = binding.imageMovies
        val type = binding.type
        val cardView = binding.cardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.titleTextView.text = movie.title
        holder.yearTextView.text = movie.year
        holder.type.text = movie.type
        //on item click listener
        holder.cardView.setOnClickListener {
            listener?.let {
                it(this.movies[position])
            }
        }
        //Image loading using glide
        Glide.with(holder.itemView.context)
            .load(movie.poster)
            .apply(RequestOptions()).placeholder(R.drawable.ic_loading)
            .into(holder.movieImage)
    }

    override fun getItemCount() = movies.size

    fun itemClickListener(l: (Movie) -> Unit) {
        listener = l
    }
}
