package purwo.moviedbapps.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import purwo.moviedbapps.view.ui.movies.DetailActivity
import purwo.moviedbapps.R
import purwo.moviedbapps.model.movie.MovieResults
import purwo.moviedbapps.utils.Constants.Companion.BASE_URL_IMAGE

/**
 * Created by Purwo on 08/07/2020.
 */
class ListMoviesAdapter(val mContext: Context, val listMovies: List<MovieResults>) :
    RecyclerView.Adapter<ListMoviesAdapter.ListViewHolder>() {
    var mList: List<MovieResults> = listMovies

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_movie, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val movie = mList[position]
        Glide.with(holder.itemView.context)
            .load(BASE_URL_IMAGE + movie.poster_path)
            .apply(RequestOptions().override(55, 55))
            .into(holder.imgPoster)
        holder.tvTitle.text = movie.original_title
        holder.tvDesc.text = movie.overview
        holder.tvReleaseDate.text = movie.release_date


        holder.itemView.setOnClickListener {
            val i = Intent(mContext, DetailActivity::class.java)
            i.putExtra("movieData", movie)
            mContext.startActivity(i)
        }
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tv_movie_title)
        var tvDesc: TextView = itemView.findViewById(R.id.tv_movie_desc)
        var tvReleaseDate: TextView = itemView.findViewById(R.id.tv_movie_release_date)
        var imgPoster: ImageView = itemView.findViewById(R.id.img_movie_poster)
    }

    fun updateMovieList(movieList: List<MovieResults>) {
        this.mList = movieList
        notifyDataSetChanged()
    }
}