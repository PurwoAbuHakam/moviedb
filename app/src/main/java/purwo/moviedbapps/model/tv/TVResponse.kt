package purwo.moviedbapps.model.tv

/**
 * Created by Purwo on 08/07/2020.
 */

class TVResponse {
    var page: Int = 0
    var total_results: Int = 0
    var total_pages: Int = 0
    var results: List<TVResults> = arrayListOf()
}