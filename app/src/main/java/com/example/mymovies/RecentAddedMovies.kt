package com.example.mymovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_box_office.*
import kotlinx.android.synthetic.main.activity_recent_added_movies.*
import okhttp3.*
import java.io.IOException

class RecentAddedMovies : AppCompatActivity() {


    var recentMoviesList : MutableList<Movie>? = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_added_movies)


        getRecentAddedMovies()

        recyclerview_recent_added_movies.layoutManager = LinearLayoutManager(this  )
    }

    fun getRecentAddedMovies(){
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://movies-tvshows-data-imdb.p.rapidapi.com/?page=2&type=get-recently-added-movies")
            .get()
            .addHeader("x-rapidapi-key", "6bc05629famshf143cd5a00ee9e4p1a6d65jsn5da2c79af3a3")
            .addHeader("x-rapidapi-host", "movies-tvshows-data-imdb.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(responseCallback = object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("fail to enqueue the API ")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
//                println(body)
                val gson = GsonBuilder().create()
                var dataOfAllMovies = gson.fromJson(body , DataOfAllMovies::class.java)
                getMoviesImages(dataOfAllMovies)
            }

        })

    }


    fun getMoviesImages(dataOfAllMovies: DataOfAllMovies){
        val numOfMovies = dataOfAllMovies.movie_results.count() -1

        for (i in 0 .. numOfMovies){
//            var movieImageGetImageByAPI = "https://movies-tvshows-data-imdb.p.rapidapi.com/?imdb="
//            var currentMovieData = dataOfAllMovies.movie_results.get(i)
//            movieImageGetImageByAPI = movieImageGetImageByAPI + currentMovieData.imdb_id
//            movieImageGetImageByAPI= movieImageGetImageByAPI + "&type=get-movies-images-by-imdb"
//            val client = OkHttpClient()
//
//            val request = Request.Builder()
//                .url(movieImageGetImageByAPI)
//                .get()
//                .addHeader("x-rapidapi-key", "6bc05629famshf143cd5a00ee9e4p1a6d65jsn5da2c79af3a3")
//                .addHeader("x-rapidapi-host", "movies-tvshows-data-imdb.p.rapidapi.com")
//                .build()


            var movieImageGetImageByAPI = "http://www.omdbapi.com/?i="
            var currentMovieData = dataOfAllMovies.movie_results.get(i)
            movieImageGetImageByAPI = movieImageGetImageByAPI + currentMovieData.imdb_id
            movieImageGetImageByAPI= movieImageGetImageByAPI + "&apikey=a4cb460e"
            val client = OkHttpClient()

            val request = Request.Builder()
                .url(movieImageGetImageByAPI)
                .get()
                .build()




            val response = client.newCall(request).enqueue(responseCallback = object: Callback {

                override fun onFailure(call: Call, e: IOException) {
                    println("fail to enqueue the API ")
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
//                println(body)
                    val gson = GsonBuilder().create()
                    val moviePoster = gson.fromJson(body , MovieImage::class.java)
                    var currentMovie = Movie(currentMovieData.title, currentMovieData.year, currentMovieData.imdb_id , moviePoster)

                    recentMoviesList!!.add(currentMovie)


                }

            })

        }
//
        Thread(Runnable {
            this@RecentAddedMovies.runOnUiThread(java.lang.Runnable {

                var i=0;
                while(i<Int.MAX_VALUE){
                    i++
                }

                recyclerview_recent_added_movies.adapter = BoxOfficeAdapter(recentMoviesList)//(moviesList)
            })
        }).start()


//        recycleview_box_office.adapter = BoxOfficeAdapter()

    }
}