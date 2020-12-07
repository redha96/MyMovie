package com.example.mymovies

import java.net.URL

data class MovieData (var title : String , var year : String , var imdb_id : String )

data class Movie (/*var movieData : MovieData*/ var title: String, var year: String, var id : String
                  , var movieImage : MovieImage)

data class DataOfAllMovies (val movie_results : List<MovieData>)

data class MovieImage (val Poster : String)