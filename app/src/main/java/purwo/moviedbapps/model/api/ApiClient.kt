package purwo.moviedbapps.model.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import purwo.moviedbapps.utils.Constants.Companion.BASE_URL

/**
 * Created by Purwo on 08/07/2020.
 */
object ApiClient {
    val instance: ApiService = Retrofit.Builder().run {
        val gson = GsonBuilder()
            .enableComplexMapKeySerialization()
            .setPrettyPrinting()
            .create()

        baseUrl(BASE_URL)
        addConverterFactory(GsonConverterFactory.create(gson))
        build()
    }.create(ApiService::class.java)
}