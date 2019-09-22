package com.example.omdbsearchapisample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omdbsearchapisample.R
import com.example.omdbsearchapisample.RoomDB.AppDatabase
import com.example.omdbsearchapisample.adapter.BookMarkAdapter
import com.example.omdbsearchapisample.model.Search
import kotlinx.android.synthetic.main.fragment_searchmovie.*

class SearchFragment : Fragment() {

    lateinit var appDatabase: AppDatabase

    lateinit var bookMarkAdapter: BookMarkAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_searchmovie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        appDatabase = AppDatabase.getInstance(context!!)!!

        bookMarkAdapter = BookMarkAdapter(context!!, appDatabase.BookMarkDao().getAllBookmark(), object : BookMarkAdapter.ISearchActions {
            override fun deleteMovieFromBookMark(imdbId: Search) {
                appDatabase.BookMarkDao().delete(imdbId)
                bookMarkAdapter.setData(appDatabase.BookMarkDao().getAllBookmark())
            }

            override fun onMovieClicked(name: String) {

                getMovieList(name)

            }

        })

        rvBookmark.adapter = bookMarkAdapter
        rvBookmark.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


        svSearchMovie.isActivated = true
        svSearchMovie.queryHint = "Enter movie/show name"

        svSearchMovie.onActionViewExpanded()
        svSearchMovie.isIconified = false

        svSearchMovie.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getMovieList(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false

            }

        })


    }

    private fun getMovieList(name: String) {

        val manager = fragmentManager
        val transaction = manager!!.beginTransaction()
        transaction.replace(R.id.fragmentcontainer, MovieListFragment().newInstance(name))
        transaction.commit()

    }


    override fun onDestroy() {
        AppDatabase.destroyInstance()
        super.onDestroy()

    }

}