package purwo.moviedbapps.model.movie

import com.google.gson.annotations.SerializedName


/**
 * Created by Purwo on 08/07/2020.
 */

class MovieResponse {
    var page: Int = 0
    @SerializedName("total_results")
    var totalResults: Int = 0
    @SerializedName("total_pages")
    var totalPages: Int = 0
    var results: List<MovieResults> = arrayListOf()
}