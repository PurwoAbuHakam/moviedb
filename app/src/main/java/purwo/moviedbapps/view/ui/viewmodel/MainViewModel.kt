package purwo.moviedbapps.view.ui.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import purwo.moviedbapps.R
import purwo.moviedbapps.view.ui.favourites.FavouritesFragment
import purwo.moviedbapps.view.ui.home.HomeFragment

/**
 * Created by Purwo on 08/07/2020.
 */

class MainViewModel : ViewModel() {
    val mFragment = MutableLiveData<Fragment>()
    val mTitle = MutableLiveData<Int>().apply { value = R.string.main_title }

    val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val fragment = HomeFragment.newInstance()
                    mFragment.value = fragment
                    mTitle.value = R.string.main_title
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_favourite -> {
                    val fragment = FavouritesFragment.newInstance()
                    mFragment.value = fragment
                    mTitle.value = R.string.fav_title
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
}
