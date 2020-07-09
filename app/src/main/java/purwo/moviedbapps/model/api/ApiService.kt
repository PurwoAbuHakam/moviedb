package purwo.moviedbapps.model.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import purwo.moviedbapps.BuildConfig
import purwo.moviedbapps.model.movie.MovieResponse
import purwo.moviedbapps.model.tv.TVResponse
import purwo.moviedbapps.utils.Constants

/**
 * Created by Purwo on 08/07/2020.
 */
interface ApiService {

    @GET("tv/popular")
    fun getTV(@Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY): Call<TVResponse>

    @GET("movie/popular")
    fun getMovie(@Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY): Call<MovieResponse>

    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") lang: String = Constants.SEARCH_API_LANG,
        @Query("query") query: String
    ): Call<MovieResponse>

    @GET("search/tv")
    fun searchTV(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") lang: String = Constants.SEARCH_API_LANG,
        @Query("query") query: String
    ): Call<TVResponse>

    @GET("discover/movie")
    fun getNewRelease(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("primary_release_date.gte") rlsDateGte: String,
        @Query("primary_release_date.lte") rlsDateLte: String
    ): Call<MovieResponse>
}