package com.example.omdbsearchapisample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.omdbsearchapisample.model.MovieDetail
import com.example.omdbsearchapisample.networking.ApiClient
import com.example.omdbsearchapisample.networking.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.activity_movie_detail.*
import io.reactivex.disposables.CompositeDisposable
import android.widget.RelativeLayout
import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


const val API_KEY = "93568a45"

class MovieDetailActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var apiInterface: ApiInterface
    private val compositeDisposable = CompositeDisposable()

    private lateinit var progressBar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        initProgressBar()
        progressBar.visibility=View.VISIBLE

//        apiInterface = ApiClient.getClient().create(ApiInterface::class.java)

        val imdbID = intent.getStringExtra("ImdbId")

        compositeDisposable.add(
                apiInterface.getMovieDetail(API_KEY, imdbID)
                        .subscribeOn(io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ movieDetail ->

                            setDataToViews(movieDetail)

                            Glide.with(applicationContext)
                                    .load(movieDetail.poster)
                                    .into(ivPoster)
                            Log.d("ImageUrl", movieDetail.poster)
                            progressBar.visibility=View.GONE

                        },
                                { error ->
                                    Log.d("Movieerro", error.localizedMessage

                                    )
                                    progressBar.visibility=View.GONE


                                })
        )


    }

    private fun setDataToViews(movieDetail: MovieDetail) {

        tvTitle.text = movieDetail.title
        tvMovieCast.text = movieDetail.actors
        tvMoviePlot.text = movieDetail.plot
        tvReleaseDate.text = movieDetail.released
        tvTimeAndGenre.text = movieDetail.runtime + "   " + movieDetail.genre

        var rating: Float = movieDetail.imdbRating.toFloat()


        movieRating.visibility=View.VISIBLE

        movieRating.rating = (rating / 2.0f)

    }

    override fun onDestroy() {

        compositeDisposable.clear()

        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    private fun initProgressBar() {
        progressBar = ProgressBar(this, null)
        progressBar.setIndeterminate(true)

        val relativeLayout = RelativeLayout(this)
        relativeLayout.gravity = Gravity.CENTER
        relativeLayout.addView(progressBar)

        val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        progressBar.setVisibility(View.INVISIBLE)

        this.addContentView(relativeLayout, params)
    }


}