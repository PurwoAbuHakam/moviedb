package purwo.moviedbapps.model.tv

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import purwo.moviedbapps.model.api.ApiClient

/**
 * Created by Purwo on 08/07/2020.
 */
class TVRepository {
    fun getTVList(onResult: (isSuccess: Boolean, response: TVResponse?) -> Unit) {

        ApiClient.instance.getTV().enqueue(object : Callback<TVResponse> {
            override fun onResponse(call: Call<TVResponse>?, response: Response<TVResponse>?) {
                if (response != null && response.isSuccessful)
                    onResult(true, response.body()!!)
                else
                    onResult(false, null)
            }

            override fun onFailure(call: Call<TVResponse>?, t: Throwable?) {
                onResult(false, null)
            }
        })
    }

    fun searchTV(searchQuery:String,onResult: (isSuccess: Boolean, response: TVResponse?) -> Unit){
        ApiClient.instance.searchTV(query = searchQuery).enqueue(object : Callback<TVResponse> {
            override fun onResponse(call: Call<TVResponse>?, response: Response<TVResponse>?) {
                if (response != null && response.isSuccessful)
                    onResult(true, response.body()!!)
                else
                    onResult(false, null)
            }

            override fun onFailure(call: Call<TVResponse>?, t: Throwable?) {
                onResult(false, null)
            }
        })
    }

    companion object {
        private var INSTANCE: TVRepository? = null
        fun getInstance() = INSTANCE
            ?: TVRepository().also {
                INSTANCE = it
            }
    }
}