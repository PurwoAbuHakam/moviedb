package purwo.moviedbapps.view.ui.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_favourites.*

import purwo.moviedbapps.R
import purwo.moviedbapps.view.adapter.FavouritePagerAdapter

class FavouritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionsPagerAdapter = FavouritePagerAdapter(requireContext(), childFragmentManager)
        view_pager_fav.adapter = sectionsPagerAdapter
        tabsFav.setupWithViewPager(view_pager_fav)
    }

    companion object {
        fun newInstance(): FavouritesFragment {
            val fragment = FavouritesFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
