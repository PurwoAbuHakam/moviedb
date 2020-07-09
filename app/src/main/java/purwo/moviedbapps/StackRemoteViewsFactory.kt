package purwo.moviedbapps

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import purwo.moviedbapps.model.content_provider.DatabaseContract
import purwo.moviedbapps.model.realm.FavouriteItem

/**
 * Created by Purwo on 08/07/2020.
 */
internal class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {
    private val mWidgetItems = ArrayList<FavouriteItem>()

    override fun onCreate() {
        getData()
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun onDataSetChanged() {
        getData()
    }

    private fun getData(){
        mWidgetItems.clear()

        val identityToken:Long = Binder.clearCallingIdentity()

        val favCursor: Cursor = mContext.contentResolver.query(
            DatabaseContract.FavouriteColumns.CONTENT_URI, null, null, null)

        favCursor.apply {
            while (moveToNext()) {
                val type = getInt(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.TYPE))
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.TITLE))
                val releaseDate = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.RELEASE_DATE))
                val overview = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.OVERVIEW))
                val posterUrl = getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.POSTER_URL))
                mWidgetItems.add(FavouriteItem(id, title, releaseDate, overview, posterUrl, type))
            }
        }

        Binder.restoreCallingIdentity(identityToken)
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)

        if(mWidgetItems.size > 0) {
            val btmp: Bitmap = Glide.with(mContext)
                .asBitmap()
                .load(mWidgetItems[position].posterUrl)
                .submit(512, 512)
                .get()

            rv.setImageViewBitmap(R.id.imageViewWidget, btmp)

            val extras = bundleOf(
                FavouriteWidget.EXTRA_ITEM to mWidgetItems[position].title
            )

            val fillInIntent = Intent()
            fillInIntent.putExtras(extras)
            rv.setOnClickFillInIntent(R.id.imageViewWidget, fillInIntent)
        }
        return rv
    }

    override fun getCount(): Int {
        return mWidgetItems.size
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {

    }

}