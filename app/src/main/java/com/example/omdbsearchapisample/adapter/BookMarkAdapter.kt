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

class BookMarkAdapter(private var context: Context, private var allBookmark: List<Search>, private var iSearchActions: ISearchActions) : RecyclerView.Adapter<BookMarkAdapter.MyViewHolder>() {


    fun setData(bookmarkList: List<Search>) {
        this.allBookmark = bookmarkList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivMoviePoster: ImageView
        var tvMovieName: TextView
        var ivMovieDelete: ImageView

        init {

            ivMoviePoster = itemView.findViewById(R.id.ivMoviePoster)
            tvMovieName = itemView.findViewById(R.id.tvMovieName)
            ivMovieDelete = itemView.findViewById(R.id.ivDelete)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_bookmark_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {

        return allBookmark.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val search = allBookmark.get(position)

        Glide.with(context)
                .load(search.Poster)
                .into(holder.ivMoviePoster)

        holder.tvMovieName.text = search.Title

        holder.ivMovieDelete.setOnClickListener {

            iSearchActions.deleteMovieFromBookMark(search)

        }

        holder.ivMoviePoster.setOnClickListener {

            iSearchActions.onMovieClicked(search.Title)

        }


    }


    interface ISearchActions {
        fun deleteMovieFromBookMark(imdbId: Search)
        fun onMovieClicked(name: String)
    }
}