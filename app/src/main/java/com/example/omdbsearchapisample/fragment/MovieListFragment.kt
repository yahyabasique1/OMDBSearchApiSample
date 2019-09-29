package com.example.omdbsearchapisample.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.omdbsearchapisample.RoomDB.AppDatabase
import com.example.omdbsearchapisample.adapter.API_TYPE
import com.example.omdbsearchapisample.adapter.LOCAL_DB
import com.example.omdbsearchapisample.adapter.MovieAdapter
import com.example.omdbsearchapisample.model.Search
import com.example.omdbsearchapisample.networking.ApiInterface
import kotlinx.android.synthetic.main.fragment_movie_layout.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers.io
import android.view.MenuInflater
import com.example.omdbsearchapisample.*
import com.example.omdbsearchapisample.RoomDB.BookMarkDao
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class MovieListFragment() : DaggerFragment() {


    var loading = false
    var pastVisiblesItems = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var moviesList = ArrayList<Search>()


    private var listType: String = "Api"

    private val compositeDisposable = CompositeDisposable()


    var movieName: String = ""

    @Inject
    lateinit var apiInterface: ApiInterface
    lateinit var adapter: MovieAdapter
    private var layoutManager: LinearLayoutManager? = null
    private var pageNumber = 1

    @Inject
    lateinit var appDatabase: AppDatabase

    private var isLastPage = false

    lateinit var bookMarkDao: BookMarkDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
//        (activity!!.application as ApplicationClass).getComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_layout, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        movieListVM=ViewModelProviders.of(this).get(MovieListVM::class.java)
        layoutManager = LinearLayoutManager(context)

        bookMarkDao = appDatabase.BookMarkDao()

        rvMovieList.layoutManager = layoutManager


        adapter = MovieAdapter(activity!!, object : MovieAdapter.IMovieAction {
            override fun delMovieFromBookMark(search: Search) {

            }

            override fun sendImdbId(imdb: String) {

                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra("ImdbId", imdb)
                startActivity(intent)

            }

            override fun addMovieToBookMark(search: Search) {
                if (listType.equals("Api")) {

                    bookMarkDao.insertAll(search)
                    Toast.makeText(context, "Added to watchlist", Toast.LENGTH_SHORT).show()

                } else {

                    bookMarkDao.delete(search)
                    bookMarkDao.getAllBookmark().let {
                        adapter.setMovieList(it, LOCAL_DB)
                    }
                    Toast.makeText(context, "Deleted from watchlist", Toast.LENGTH_SHORT).show()

                }
            }

        })

        val args = arguments

        if (args != null) {

            movieName = args.getString("movieName", "friends")
            loadMovieData(movieName, pageNumber++)


        } else {

            movieName = "fight"
            loadMovieData(movieName, pageNumber++)

        }


        textView.setOnClickListener {
            //
            val manager = fragmentManager
            val transaction = manager!!.beginTransaction()
            transaction.replace(R.id.fragmentcontainer, SearchFragment())
            transaction.addToBackStack("SearchFragment")
            transaction.commit()
        }

        onScrollListener()

    }


    private fun loadMovieData(s: String, pagenum: Int) {

        compositeDisposable.add(
            apiInterface.getSearchMovieList(API_KEY, s, pagenum)
                .subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ searchApiResponse ->

                    if (searchApiResponse.Response.equals("True")) {
                        rvMovieList.adapter = adapter
                        loading = false
                        moviesList.addAll(searchApiResponse.Search)
                        adapter.setMovieList(moviesList, API_TYPE)
                        listType = "Api"
                        isLastPage = false

                    } else if (searchApiResponse.Response.equals("False")) {
                        isLastPage = true
                        Toast.makeText(context, "End of Results", Toast.LENGTH_LONG).show()
                    }
                },
                    { error ->

                        Toast.makeText(context, error.localizedMessage, Toast.LENGTH_SHORT).show()
                    })
        )
    }


    fun newInstance(moviename: String): MovieListFragment {
        val f = MovieListFragment()
        // Supply index input as an argument.
        val args = Bundle()
        args.putString("movieName", moviename)
        f.setArguments(args)
        return f
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        if (appDatabase.isOpen) {
            appDatabase.close()
        }
        super.onDestroy()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuBookMark -> {

                bookMarkDao.getAllBookmark()?.let {
                    if (it.isEmpty()) {
                        Toast.makeText(context, "Your watch list is empty", Toast.LENGTH_LONG)
                            .show()
                        adapter.setMovieList(moviesList, API_TYPE)
                        listType = "Api"
                    } else {
                        adapter.setMovieList(it, LOCAL_DB)
                        listType = "LocalDb"


                    }


                }


            }

            R.id.menuMovieList -> {
                adapter.setMovieList(moviesList, API_TYPE)
                listType = "Api"
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun onScrollListener() {

        rvMovieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0)
                //check for scroll down
                {
                    visibleItemCount = layoutManager!!.getChildCount()
                    totalItemCount = layoutManager!!.getItemCount()
                    pastVisiblesItems = layoutManager!!.findFirstVisibleItemPosition()

                    if (loading == false && listType.equals("Api") && isLastPage == false) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            Log.v("...", "Last Item Wow !")
                            loading = true

                            loadMovieData(movieName, pageNumber++)
                            Log.d("pagenumber", "$pageNumber ")

                        }
                    }
                }
            }
        })
    }


}



