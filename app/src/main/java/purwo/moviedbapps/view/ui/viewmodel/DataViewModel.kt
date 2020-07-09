package purwo.moviedbapps.view.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import purwo.moviedbapps.model.movie.MovieRepository
import purwo.moviedbapps.model.movie.MovieResults
import purwo.moviedbapps.model.tv.TVRepository
import purwo.moviedbapps.model.tv.TVResults

/**
 * Created by Purwo on 08/07/2020.
 */
class DataViewModel : ViewModel() {
    val listDataTV = MutableLiveData<List<TVResults>>()
    val listDataSearchTV = MutableLiveData<List<TVResults>>()
    val listDataMovie = MutableLiveData<List<MovieResults>>()
    val listDataSearchMovie = MutableLiveData<List<MovieResults>>()
    val dataLoading = MutableLiveData<Boolean>().apply { value = false }
    val empty = MutableLiveData<Boolean>().apply { value = false }
    val searchQueryMovie = MutableLiveData<String>().apply { value = "" }
    val searchQueryTV = MutableLiveData<String>().apply { value = "" }

    fun fetchTVList() {
        dataLoading.value = true
        TVRepository.getInstance().getTVList { isSuccess, response ->
            dataLoading.value = false
            if (isSuccess) {
                listDataTV.value = response?.results
                empty.value = false
            } else {
                empty.value = true
            }
        }
    }

    fun searchTVList(searchQuery: String) {
        searchQueryTV.value = searchQuery
        dataLoading.value = true
        TVRepository.getInstance().searchTV(searchQuery = searchQuery) { isSuccess, response ->
            dataLoading.value = false
            if (isSuccess) {
                listDataSearchTV.value = response?.results
                empty.value = false
            } else {
                empty.value = true
            }
        }
    }

    fun fetchMoviesList() {
        dataLoading.value = true
        MovieRepository.getInstance().getMovieList { isSuccess, response ->
            dataLoading.value = false
            if (isSuccess) {
                listDataMovie.value = response?.results
                empty.value = false
            } else {
                empty.value = true
            }
        }
    }

    fun searchMovieList(searchQuery: String) {
        searchQueryMovie.value = searchQuery
        dataLoading.value = true
        MovieRepository.getInstance()
            .searchMovie(searchQuery = searchQuery) { isSuccess, response ->
                dataLoading.value = false
                if (isSuccess) {
                    listDataSearchMovie.value = response?.results
                    empty.value = false
                } else {
                    empty.value = true
                }
            }
    }
}