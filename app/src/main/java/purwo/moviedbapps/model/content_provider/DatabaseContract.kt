package purwo.moviedbapps.model.content_provider

import android.net.Uri
import android.provider.BaseColumns

/**
 * Created by Purwo on 08/07/2020.
 */
object DatabaseContract {
    const val AUTHORITY = "purwo.moviedbapps"
    const val SCHEME = "content"

    class FavouriteColumns : BaseColumns {

        companion object {
            const val TABLE_NAME = "favourite"
            const val _ID = "_id"
            const val TITLE = "title"
            const val RELEASE_DATE = "release_date"
            const val OVERVIEW = "overview"
            const val POSTER_URL = "poster_url"
            const val TYPE = "type"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}