package com.example.mymovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_box_office.*
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import java.io.IOException





class BoxOffice : AppCompatActivity() {

    //var moviesList = mutableListOf<Movie>()
    var moviesList : MutableList<Movie>? = ArrayList<Movie>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_box_office)





        getBoxOfficeMovies()
//        println("the size of the list before fetch the data is : " + moviesList?.size)

        recycleview_box_office.layoutManager = LinearLayoutManager(this  )


//        recycleview_box_office.adapter = BoxOfficeAdapter()
//
//        println("the size of the list after fetch the data is : " + moviesList?.size)

    }

    override fun onStart() {
        super.onStart()
//        getBoxOfficeMovies()
//        println("the size of the list is : " +moviesList?.size)

    }

    fun getBoxOfficeMovies(){
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://movies-tvshows-data-imdb.p.rapidapi.com/?type=get-boxoffice-movies&page=1")
            .get()
            .addHeader("x-rapidapi-key", "6bc05629famshf143cd5a00ee9e4p1a6d65jsn5da2c79af3a3")
            .addHeader("x-rapidapi-host", "movies-tvshows-data-imdb.p.rapidapi.com")
            .build()

        val response = client.newCall(request).enqueue(responseCallback = object: Callback {
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

//                    moviesList!!.add(currentMovie)
                    addCurrentMovieToTheList(currentMovie)



                }





            })

        }
//

        Thread(Runnable {
            this@BoxOffice.runOnUiThread(java.lang.Runnable {

                var i=0;
                while(i<Int.MAX_VALUE){
                    i++
                }

                recycleview_box_office.adapter = BoxOfficeAdapter(moviesList)//(moviesList)
            })
        }).start()


//        recycleview_box_office.adapter = BoxOfficeAdapter()

    }

    fun addCurrentMovieToTheList(currentMovie: Movie){
//        moviesList!!.add(currentMovie)//add(currentMovie)
        moviesList!!.add(currentMovie)//add(currentMovie)
        //println(currentMovie.title + "........................" + moviesList.size )

    }
}