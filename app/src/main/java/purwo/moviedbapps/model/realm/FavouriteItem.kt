package purwo.moviedbapps.model.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Purwo on 08/07/2020.
 */

open class FavouriteItem (
    @PrimaryKey
    var id: Int = 0,
    var title: String = "",
    var releaseDate: String = "",
    var overview: String = "",
    var posterUrl: String = "",
    var type: Int = 0
    ) : RealmObject()