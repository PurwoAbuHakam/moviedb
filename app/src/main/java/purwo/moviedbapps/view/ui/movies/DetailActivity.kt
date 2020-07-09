package purwo.moviedbapps.view.ui.movies

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import purwo.moviedbapps.FavouriteWidget
import purwo.moviedbapps.R
import purwo.moviedbapps.model.movie.MovieResults
import purwo.moviedbapps.model.realm.FavouriteItem
import purwo.moviedbapps.utils.Constants
import purwo.moviedbapps.utils.Constants.Companion.BASE_URL_IMAGE
import purwo.moviedbapps.view.ui.viewmodel.FavouriteViewModel

class DetailActivity : AppCompatActivity() {
    lateinit var movie: MovieResults
    lateinit var favViewModel: FavouriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        movie = intent.getParcelableExtra("movieData") as MovieResults
        favViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FavouriteViewModel::class.java
        )

        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.title = movie.title
            actionbar.setDisplayHomeAsUpEnabled(true)
        }

        tvTitle.text = movie.title
        tvDescription.text = movie.overview
        tvRelease.text = movie.release_date

        Glide.with(this)
            .load(BASE_URL_IMAGE + movie.poster_path)
            .into(ivPoster)

        favViewModel.success.observe(this, Observer<Boolean> {
            if (it) {
                Toast.makeText(this, getString(R.string.added_favourites), Toast.LENGTH_LONG).show()
            }
        })
    }


    private var favItem: MenuItem? = null
    private var unfavItem: MenuItem? = null
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bar_fav, menu)
        favItem = menu.findItem(R.id.fav_menu)
        unfavItem = menu.findItem(R.id.unfav_menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.unfav_menu -> {
                //Add To Favorite
                favViewModel.saveMovieToFavourite(movie)
                FavouriteWidget.updateWidget(this)
                unfavItem!!.isVisible = false
                favItem!!.isVisible = true
                return true
            }
            R.id.fav_menu -> {
                favViewModel.deleteFavourite(movie.id, Constants.TYPE_MOVIE)
                FavouriteWidget.updateWidget(this)
                unfavItem!!.isVisible = true
                favItem!!.isVisible = false
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
