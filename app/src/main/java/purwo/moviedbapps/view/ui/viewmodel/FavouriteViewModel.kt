package purwo.moviedbapps.view.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import purwo.moviedbapps.model.favourite.FavouriteRepository
import purwo.moviedbapps.model.movie.MovieResults
import purwo.moviedbapps.model.realm.FavouriteItem
import purwo.moviedbapps.model.tv.TVResults
import purwo.moviedbapps.utils.Constants

/**
 * Created by Purwo on 08/07/2020.
 */
class FavouriteViewModel : ViewModel() {
    val listMovie = MutableLiveData<List<FavouriteItem>>()
    val listTV = MutableLiveData<List<FavouriteItem>>()
    val searchData = MutableLiveData<FavouriteItem>()
    val success = MutableLiveData<Boolean>().apply { value = false }
    val deleteSuccess = MutableLiveData<Boolean>().apply { value = false }

    fun saveMovieToFavourite(movie: MovieResults) {
        val movieRealm = FavouriteItem()
        movieRealm.id = movie.id
        movieRealm.title = movie.title
        movieRealm.releaseDate = movie.release_date
        movieRealm.overview = movie.overview
        movieRealm.posterUrl = Constants.BASE_URL_IMAGE + movie.poster_path
        movieRealm.type = Constants.TYPE_MOVIE

        FavouriteRepository.getInstance().saveToFavourite(movieRealm)
        success.value = true
    }

    fun saveTvToFavourite(tv: TVResults) {
        val tvRealm = FavouriteItem()
        tvRealm.id = tv.id
        tvRealm.title = tv.name
        tvRealm.releaseDate = tv.first_air_date
        tvRealm.overview = tv.overview
        tvRealm.posterUrl = Constants.BASE_URL_IMAGE + tv.poster_path
        tvRealm.type = Constants.TYPE_TV

        FavouriteRepository.getInstance().saveToFavourite(tvRealm)
        success.value = true
    }

    fun getMovieFavList() {
        listMovie.value = FavouriteRepository.getInstance().favouriteMovies()
    }

    fun getTvFavList() {
        listTV.value = FavouriteRepository.getInstance().favouriteTVs()
    }

    fun getFavItem(id: Int, type: Int) {
        searchData.value = FavouriteRepository.getInstance().itemFavourite(id, type)
    }

    fun deleteFavourite(id: Int, type: Int) {
        FavouriteRepository.getInstance().deleteFavourite(id, type)
        deleteSuccess.value = true
    }
}