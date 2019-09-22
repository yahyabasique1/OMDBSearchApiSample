package com.example.omdbsearchapisample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.omdbsearchapisample.R
import com.example.omdbsearchapisample.model.Search

const val LOCAL_DB = "localdb"
const val API_TYPE = "apitype"

class MovieAdapter(private var context: Context, private var iMovieAction: IMovieAction) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.adapter_movie_layout, parent, false)


        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {

        return moviesList.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val search = moviesList.get(position)



        if (listType.equals(LOCAL_DB)) {
            holder.tvAddToBookmark.text = "Remove From Wishlist"
        } else {
            holder.tvAddToBookmark.text = "Add to wishlist"
        }

        Glide.with(context)
                .load(search.Poster)
                .into(holder.ivMoviePoster)

        holder.tvMovieYear.text = search.Year
        holder.tvMovieName.text = search.Title

        holder.itemView.setOnClickListener {
            iMovieAction.sendImdbId(search.imdbID)
        }

        holder.tvAddToBookmark.setOnClickListener {
            iMovieAction.addMovieToBookMark(search)

        }

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivMoviePoster: ImageView
        var tvMovieName: TextView
        var tvMovieYear: TextView

        var tvAddToBookmark: TextView

        init {

            tvAddToBookmark = itemView.findViewById(R.id.tvAddToBookMark)
            ivMoviePoster = itemView.findViewById(R.id.ivMoviePoster)
            tvMovieName = itemView.findViewById(R.id.tvMovieName)
            tvMovieYear = itemView.findViewById(R.id.tvMovieYear)

        }

    }

    private lateinit var listType: String
    lateinit var moviesList: List<Search>


    fun setMovieList(moviesList: List<Search>, string: String) {
        this.moviesList = moviesList
        notifyDataSetChanged()
        this.listType = string

    }

    interface IMovieAction {
        fun sendImdbId(imdb: String)

        fun addMovieToBookMark(search: Search)

        fun delMovieFromBookMark(search: Search)
    }

}
