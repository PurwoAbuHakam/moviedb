package purwo.moviedbapps.model.movie

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Purwo on 08/07/2020.
 */
@Parcelize
data class MovieResults(
    var video: Boolean,
    var adult: Boolean,
    var genre_ids: ArrayList<Int>,
    var original_title: String,
    var title: String,
    var popularity: Double,
    var vote_count: Int,
    var release_date: String,
    var backdrop_path: String,
    var original_language: String,
    var id: Int,
    var vote_average: Double,
    var overview: String,
    var poster_path: String
) : Parcelable