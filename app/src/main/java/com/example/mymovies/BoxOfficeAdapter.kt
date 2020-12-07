package com.example.mymovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_row.view.*

class BoxOfficeAdapter(val moviesList: MutableList<Movie>?) : RecyclerView.Adapter<BoxOfficeViewHolder>() {
    override fun getItemCount(): Int {
        println("the list size in BoxOfficeAdapter is " + moviesList!!.size)
        return moviesList!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxOfficeViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.movie_row, parent, false)
        return BoxOfficeViewHolder(cellForRow)
    }


    override fun onBindViewHolder(holder: BoxOfficeViewHolder, position: Int) {

        if(holder != null) {
            val movie : Movie = moviesList!![position]
            var movieSortingPosition = position+1
            holder.view.position_text_view.setText("$movieSortingPosition")
            holder!!.view.movie_title_textview.setText(movie.title)
            holder!!.view.movie_year_textview.setText(movie.year)
            Glide.with(holder.view.movie_image_view).load(movie.movieImage.Poster).into(holder.view.movie_image_view)
        }
        



    }


}


class BoxOfficeViewHolder(val view : View) : RecyclerView.ViewHolder(view) {

}