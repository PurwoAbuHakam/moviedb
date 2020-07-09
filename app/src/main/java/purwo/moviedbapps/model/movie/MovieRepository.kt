package purwo.moviedbapps.model.movie

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import purwo.moviedbapps.model.api.ApiClient

/**
 * Created by Purwo on 08/07/2020.
 */
class MovieRepository {
    fun getMovieList(onResult: (isSuccess: Boolean, response: MovieResponse?) -> Unit) {

        ApiClient.instance.getMovie().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>?,
                response: Response<MovieResponse>?
            ) {
                if (response != null && response.isSuccessful)
                    onResult(true, response.body()!!)
                else
                    onResult(false, null)
            }

            override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {
                onResult(false, null)
            }
        })
    }


    fun searchMovie(
        searchQuery: String,
        onResult: (isSuccess: Boolean, response: MovieResponse?) -> Unit
    ) {
        ApiClient.instance.searchMovie(query = searchQuery)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>?,
                    response: Response<MovieResponse>?
                ) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {
                    onResult(false, null)
                }
            })
    }

    companion object {
        private var INSTANCE: MovieRepository? = null
        fun getInstance() = INSTANCE
            ?: MovieRepository().also {
                INSTANCE = it
            }
    }
}