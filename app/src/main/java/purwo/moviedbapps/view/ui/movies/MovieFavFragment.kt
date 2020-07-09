package purwo.moviedbapps.view.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import purwo.moviedbapps.R
import purwo.moviedbapps.model.realm.FavouriteItem
import purwo.moviedbapps.view.adapter.ListFavouriteAdapter
import purwo.moviedbapps.view.ui.MainActivity
import purwo.moviedbapps.view.ui.viewmodel.FavouriteViewModel

class MovieFavFragment : Fragment() {
    private lateinit var rvMovies: RecyclerView
    private var listMovieResult: List<FavouriteItem> = listOf()
    private lateinit var listMovieAdapter: ListFavouriteAdapter
    private lateinit var favViewModel: FavouriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_fav, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rvMovies = view.findViewById(R.id.rv_movies_fav)
        rvMovies.setHasFixedSize(true)

        favViewModel = MainActivity.FavouriteViewModel()

        showRecyclerList()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        favViewModel.listMovie.observe(this, Observer<List<FavouriteItem>> {
            listMovieAdapter.updateFavouriteList(it)
        })
    }

    override fun onResume() {
        super.onResume()
        favViewModel.getMovieFavList()
    }

    private fun showRecyclerList() {
        rvMovies.layoutManager = LinearLayoutManager(context)
        listMovieAdapter = ListFavouriteAdapter(context!!.applicationContext, listMovieResult)
        rvMovies.adapter = listMovieAdapter
    }
}
