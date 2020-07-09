package purwo.moviedbapps.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import io.realm.Realm
import io.realm.kotlin.where
import purwo.moviedbapps.FavouriteWidget
import purwo.moviedbapps.model.content_provider.DatabaseContract
import purwo.moviedbapps.model.content_provider.DatabaseContract.AUTHORITY
import purwo.moviedbapps.model.content_provider.DatabaseContract.FavouriteColumns.Companion.TABLE_NAME
import purwo.moviedbapps.model.realm.FavouriteItem


class FavouriteProvider : ContentProvider() {

    companion object {
        private const val FAVOURITE = 1
        private const val FAVOURITE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVOURITE)
            sUriMatcher.addURI(
                AUTHORITY,
                "$TABLE_NAME/#",
                FAVOURITE_ID
            )
        }
    }

    override fun onCreate(): Boolean {
        Realm.init(context!!)
        return true
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        var realm: Realm = Realm.getDefaultInstance()
        val match = sUriMatcher.match(uri)
        var returnUri: Uri

        var favItem = FavouriteItem()
        favItem.id = values!!.getAsInteger(DatabaseContract.FavouriteColumns._ID)
        favItem.title = values.getAsString(DatabaseContract.FavouriteColumns.TITLE)
        favItem.releaseDate = values.getAsString(DatabaseContract.FavouriteColumns.RELEASE_DATE)
        favItem.posterUrl = values.getAsString(DatabaseContract.FavouriteColumns.POSTER_URL)
        favItem.overview = values.getAsString(DatabaseContract.FavouriteColumns.OVERVIEW)
        favItem.type = values.getAsInteger(DatabaseContract.FavouriteColumns.TYPE)

        realm.beginTransaction()
        realm.copyToRealmOrUpdate(favItem)
        realm.commitTransaction()
        returnUri = ContentUris.withAppendedId(DatabaseContract.FavouriteColumns.CONTENT_URI, 1)

        context?.contentResolver?.notifyChange(uri, null)
        FavouriteWidget.updateWidget(context)

        return returnUri
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

        val myCursor = MatrixCursor(
            arrayOf(
                DatabaseContract.FavouriteColumns._ID,
                DatabaseContract.FavouriteColumns.TITLE,
                DatabaseContract.FavouriteColumns.RELEASE_DATE,
                DatabaseContract.FavouriteColumns.POSTER_URL,
                DatabaseContract.FavouriteColumns.OVERVIEW,
                DatabaseContract.FavouriteColumns.TYPE
            )
        )

        val realm: Realm = Realm.getDefaultInstance()
        var dataResult: List<FavouriteItem> = listOf()
        when (sUriMatcher.match(uri)) {
            FAVOURITE -> dataResult = realm.where<FavouriteItem>().findAll() as List<FavouriteItem>
            FAVOURITE_ID -> dataResult =
                realm.where<FavouriteItem>().equalTo("id", uri.lastPathSegment?.toString())
                    .findAll() as List<FavouriteItem>
        }

        for (data: FavouriteItem in dataResult) {
            val rowData = arrayOf(
                data.id, data.title, data.releaseDate, data.posterUrl
                , data.overview, data.type
            )
            myCursor.addRow(rowData)
        }

        myCursor.setNotificationUri(context?.contentResolver, uri)
        return myCursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var realm: Realm = Realm.getDefaultInstance()
        val id = ContentUris.parseId(uri).toString().toInt()

        val todelete =
            realm.where<FavouriteItem>().equalTo("id", id).findFirst() as FavouriteItem

        realm.beginTransaction()
        todelete.deleteFromRealm()
        realm.commitTransaction()

        context?.contentResolver?.notifyChange(uri, null)
        FavouriteWidget.updateWidget(context)

        return 1
    }
}
