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
import purwo.moviedbapps.R
import purwo.moviedbapps.model.realm.FavouriteItem
import purwo.moviedbapps.view.ui.favourites.FavouriteDetailActivity

/**
 * Created by Purwo on 08/07/2020.
 */
class ListFavouriteAdapter(val mContext: Context, val listItems: List<FavouriteItem>) :
    RecyclerView.Adapter<ListFavouriteAdapter.ListViewHolder>() {
    var mList: List<FavouriteItem> = listItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_movie, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = mList[position]
        Glide.with(holder.itemView.context)
            .load(item.posterUrl)
            .apply(RequestOptions().override(55, 55))
            .into(holder.imgPoster)
        holder.tvTitle.text = item.title
        holder.tvDesc.text = item.overview
        holder.tvReleaseDate.text = item.releaseDate


        holder.itemView.setOnClickListener {
            val i = Intent(mContext, FavouriteDetailActivity::class.java)
            i.putExtra("id", item.id)
            i.putExtra("type", item.type)
            mContext.startActivity(i)
        }
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tv_movie_title)
        var tvDesc: TextView = itemView.findViewById(R.id.tv_movie_desc)
        var tvReleaseDate: TextView = itemView.findViewById(R.id.tv_movie_release_date)
        var imgPoster: ImageView = itemView.findViewById(R.id.img_movie_poster)
    }

    fun updateFavouriteList(movieList: List<FavouriteItem>) {
        this.mList = movieList
        notifyDataSetChanged()
    }
}