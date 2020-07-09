package purwo.moviedbapps.model.tv

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Purwo on 08/07/2020.
 */
@Parcelize
data class TVResults (
    var original_name: String,
    var genre_ids: ArrayList<Int>,
    var name: String,
    var popularity: Double,
    var origin_country: ArrayList<String>,
    var vote_count: Int,
    var first_air_date: String,
    var backdrop_path: String,
    var original_language: String,
    var id: Int,
    var vote_average: Double,
    var overview: String,
    var poster_path: String
) : Parcelable