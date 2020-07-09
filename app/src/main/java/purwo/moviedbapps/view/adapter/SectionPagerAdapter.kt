package purwo.moviedbapps.view.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import purwo.moviedbapps.R
import purwo.moviedbapps.view.ui.movies.MovieFragment
import purwo.moviedbapps.view.ui.tvshow.TvShowFragment

/**
 * Created by Purwo on 08/07/2020.
 */
class SectionPagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val TAB_TITLES =
        arrayOf(mContext.getString(R.string.movies), mContext.getString(R.string.tv_shows))

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment =
                MovieFragment()
            1 -> fragment =
                TvShowFragment()
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        return 2
    }

}