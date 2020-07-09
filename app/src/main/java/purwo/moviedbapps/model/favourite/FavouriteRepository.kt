package purwo.moviedbapps.model.favourite

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import io.realm.Realm
import io.realm.kotlin.where
import purwo.moviedbapps.model.content_provider.DatabaseContract
import purwo.moviedbapps.model.realm.FavouriteItem
import purwo.moviedbapps.utils.Constants
import purwo.moviedbapps.view.ui.MainActivity

/**
 * Created by Purwo on 08/07/2020.
 */
class FavouriteRepository {

    fun saveToFavourite(item: FavouriteItem) {
        /*val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(item)
        realm.commitTransaction()*/

        //Content Provider
        val values = ContentValues(6)
        values.put(DatabaseContract.FavouriteColumns._ID, item.id)
        values.put(DatabaseContract.FavouriteColumns.TITLE, item.title)
        values.put(DatabaseContract.FavouriteColumns.RELEASE_DATE, item.releaseDate)
        values.put(DatabaseContract.FavouriteColumns.POSTER_URL, item.posterUrl)
        values.put(DatabaseContract.FavouriteColumns.OVERVIEW, item.overview)
        values.put(DatabaseContract.FavouriteColumns.TYPE, item.type)

        MainActivity.mContentResolver.insert(DatabaseContract.FavouriteColumns.CONTENT_URI, values)
    }

    fun favouriteMovies(): List<FavouriteItem> {
        val favCursor: Cursor = MainActivity.mContentResolver.query(
            DatabaseContract.FavouriteColumns.CONTENT_URI,
            null,
            null,
            null
        )
        var listMovie: ArrayList<FavouriteItem> = arrayListOf()
        favCursor.apply {
            while (moveToNext()) {
                val type = getInt(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.TYPE))
                if (type == Constants.TYPE_MOVIE) {
                    val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns._ID))
                    val title =
                        getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.TITLE))
                    val releaseDate =
                        getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.RELEASE_DATE))
                    val overview =
                        getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.OVERVIEW))
                    val posterUrl =
                        getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.POSTER_URL))
                    listMovie.add(FavouriteItem(id, title, releaseDate, overview, posterUrl, type))
                }
            }
        }

        return listMovie
    }

    fun favouriteTVs(): List<FavouriteItem> {
        /*val realm: Realm = Realm.getDefaultInstance()
        return realm.where<FavouriteItem>().equalTo(
            "type",
            Constants.TYPE_TV
        ).findAll() as List<FavouriteItem>*/

        val favCursor: Cursor = MainActivity.mContentResolver.query(
            DatabaseContract.FavouriteColumns.CONTENT_URI,
            null,
            null,
            null
        )
        var listTV: ArrayList<FavouriteItem> = arrayListOf()
        favCursor.apply {
            while (moveToNext()) {
                val type = getInt(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.TYPE))
                if (type == Constants.TYPE_TV) {
                    val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns._ID))
                    val title =
                        getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.TITLE))
                    val releaseDate =
                        getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.RELEASE_DATE))
                    val overview =
                        getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.OVERVIEW))
                    val posterUrl =
                        getString(getColumnIndexOrThrow(DatabaseContract.FavouriteColumns.POSTER_URL))
                    listTV.add(FavouriteItem(id, title, releaseDate, overview, posterUrl, type))
                }
            }
        }

        return listTV
    }

    fun itemFavourite(id: Int, type: Int): FavouriteItem {
        val realm: Realm = Realm.getDefaultInstance()
        return realm.where<FavouriteItem>().equalTo("type", type).equalTo(
            "id",
            id
        ).findFirst() as FavouriteItem
    }

    fun deleteFavourite(id: Int, type: Int): Boolean {
        val deleteUri: Uri = DatabaseContract.FavouriteColumns.CONTENT_URI
            .buildUpon()
            .appendPath(id.toString())
            .build()

        MainActivity.mContentResolver.delete(deleteUri, null, null)

        return true
    }


    companion object {
        private var INSTANCE: FavouriteRepository? = null
        fun getInstance() = INSTANCE
            ?: FavouriteRepository().also {
                INSTANCE = it
            }
    }
}