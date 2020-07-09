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
import purwo.moviedbapps.view.ui.tvshow.DetailTvShowActivity
import purwo.moviedbapps.R
import purwo.moviedbapps.model.tv.TVResults
import purwo.moviedbapps.utils.Constants.Companion.BASE_URL_IMAGE

/**
 * Created by Purwo on 08/07/2020.
 */
class ListTvShowsAdapter(val mContext: Context, val listTvShows: List<TVResults>) :
    RecyclerView.Adapter<ListTvShowsAdapter.ListViewHolder>() {
    var mList: List<TVResults> = listTvShows

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_tv_shows, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val tvShows = mList[position]
        Glide.with(holder.itemView.context)
            .load(BASE_URL_IMAGE + tvShows.poster_path)
            .apply(RequestOptions().override(55, 55))
            .into(holder.imgPoster)
        holder.tvTitle.text = tvShows.name
        holder.tvDesc.text = tvShows.overview
        holder.tvReleaseDate.text = tvShows.first_air_date


        holder.itemView.setOnClickListener {
            val i = Intent(mContext, DetailTvShowActivity::class.java)
            i.putExtra("tvShowsData", tvShows)
            mContext.startActivity(i)
        }
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tv_movie_title)
        var tvDesc: TextView = itemView.findViewById(R.id.tv_movie_desc)
        var tvReleaseDate: TextView = itemView.findViewById(R.id.tv_movie_release_date)
        var imgPoster: ImageView = itemView.findViewById(R.id.img_movie_poster)
    }

    fun updateTvList(tvList: List<TVResults>) {
        this.mList = tvList
        notifyDataSetChanged()
    }
}