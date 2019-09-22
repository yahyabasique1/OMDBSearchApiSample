package com.example.omdbsearchapisample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SearchApiResponseModel(

        @Expose @SerializedName("Response") val Response: String,
        @Expose @SerializedName("Search") val Search: List<Search>,
        @Expose @SerializedName("totalResults") val totalResults: String
)

@Entity(tableName = "bookmark_table")
data class Search(
        @PrimaryKey @ColumnInfo(name = "imdbID")
        @Expose @SerializedName("imdbID") val imdbID: String,

        @ColumnInfo(name = "Title") @Expose @SerializedName("Title") val Title: String,
        @ColumnInfo(name = "Year") @Expose @SerializedName("Year") val Year: String,
        @ColumnInfo(name = "Type") @Expose @SerializedName("Type") val type: String,
        @ColumnInfo(name = "Poster") @Expose @SerializedName("Poster") val Poster: String
)

data class MovieDetail(
        @SerializedName("Actors")
        val actors: String,
        @SerializedName("Awards")
        val awards: String,
        @SerializedName("BoxOffice")
        val boxOffice: String,
        @SerializedName("Country")
        val country: String,
        @SerializedName("DVD")
        val dVD: String,
        @SerializedName("Director")
        val director: String,
        @SerializedName("Genre")
        val genre: String,
        @SerializedName("imdbID")
        val imdbID: String,
        @SerializedName("imdbRating")
        val imdbRating: String,
        @SerializedName("imdbVotes")
        val imdbVotes: String,
        @SerializedName("Language")
        val language: String,
        @SerializedName("Metascore")
        val metascore: String,
        @SerializedName("Plot")
        val plot: String,
        @SerializedName("Poster")
        val poster: String,
        @SerializedName("Production")
        val production: String,
        @SerializedName("Rated")
        val rated: String,
        @SerializedName("Ratings")
        val ratings: List<Rating>,
        @SerializedName("Released")
        val released: String,
        @SerializedName("Response")
        val response: String,
        @SerializedName("Runtime")
        val runtime: String,
        @SerializedName("Title")
        val title: String,
        @SerializedName("Type")
        val type: String,
        @SerializedName("Website")
        val website: String,
        @SerializedName("Writer")
        val writer: String,
        @SerializedName("Year")
        val year: String
)

data class Rating(
        @SerializedName("Source")
        val source: String,
        @SerializedName("Value")
        val value: String
)