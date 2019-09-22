package com.example.omdbsearchapisample.networking

import com.example.omdbsearchapisample.model.MovieDetail
import com.example.omdbsearchapisample.model.SearchApiResponseModel

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/")
    fun getSearchMovieList(@Query("apikey") apiKey: String, @Query("s") search: String, @Query("page") page: Int): Observable<SearchApiResponseModel>


    @GET("/")
    fun getMovieDetail(@Query("apikey") apiKey: String, @Query("i") imdbId: String): Observable<MovieDetail>

}
