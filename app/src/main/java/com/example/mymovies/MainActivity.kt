package com.example.mymovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException


//var moviesList : MutableList<String>? = ArrayList<String>()
//val moviesList: MutableList<String>? = ArrayList<String>()



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        get_box_office_movies_btn.setOnClickListener{
            val intent = Intent(this , BoxOffice::class.java)
            startActivity(intent)
        }


        get_recent_added_movies_btn.setOnClickListener{
            val intent = Intent(this, RecentAddedMovies::class.java)
            startActivity(intent)
        }
        //fetchData()
    }


    fun fetchData(){
        val client = OkHttpClient()

        val request = Request.Builder()
                .url("https://movies-tvshows-data-imdb.p.rapidapi.com/?type=get-airingtoday-shows&page=1")
                .get()
                .addHeader("x-rapidapi-key", "6bc05629famshf143cd5a00ee9e4p1a6d65jsn5da2c79af3a3")
                .addHeader("x-rapidapi-host", "movies-tvshows-data-imdb.p.rapidapi.com")
                .build()

        val response = client.newCall(request).enqueue(responseCallback = object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("fail to enqueue the API ")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)
            }

        })
    }


}