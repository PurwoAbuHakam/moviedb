package purwo.moviedbapps.view.ui.favourites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_favourite_detail.*
import purwo.moviedbapps.FavouriteWidget
import purwo.moviedbapps.R
import purwo.moviedbapps.model.realm.FavouriteItem
import purwo.moviedbapps.view.ui.viewmodel.FavouriteViewModel

class FavouriteDetailActivity : AppCompatActivity() {
    private lateinit var favViewModel: FavouriteViewModel
    private lateinit var favouriteDetail: FavouriteItem
    private lateinit var actionbar: ActionBar
    private var id: Int = 0
    private var type: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_detail)

        id = intent.getIntExtra("id", 0)
        type = intent.getIntExtra("type", 0)

        favViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FavouriteViewModel::class.java
        )
        favViewModel.getFavItem(id, type)

        favViewModel.searchData.observe(this, Observer<FavouriteItem> {
            favouriteDetail = it
            setContent()
        })

        favViewModel.deleteSuccess.observe(this, Observer<Boolean> {
            if (it) {
                finish()
            }
        })

        actionbar = supportActionBar as ActionBar
    }

    fun setContent() {
        if (actionbar != null) {
            actionbar.title = favouriteDetail.title
            actionbar.setDisplayHomeAsUpEnabled(true)
        }

        tvFavTitle.text = favouriteDetail.title
        tvFavDescription.text = favouriteDetail.overview
        tvFavRelease.text = favouriteDetail.releaseDate

        Glide.with(this)
            .load(favouriteDetail.posterUrl)
            .into(ivFavPoster)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bar_delete, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                //Add To Favorite
                favViewModel.deleteFavourite(id, type)
                FavouriteWidget.updateWidget(this)
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return true
        }
        return true
    }
}
