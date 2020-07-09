package purwo.moviedbapps.view.ui.movies


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_movie.*

import purwo.moviedbapps.R
import purwo.moviedbapps.model.movie.MovieResults
import purwo.moviedbapps.view.adapter.ListMoviesAdapter
import purwo.moviedbapps.view.ui.MainActivity
import purwo.moviedbapps.view.ui.viewmodel.DataViewModel

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {
    private lateinit var rvMovies: RecyclerView
    private var listMovieResult: List<MovieResults> = listOf()
    private lateinit var listMovieAdapter: ListMoviesAdapter
    private lateinit var dataViewModel: DataViewModel
    var isSearchResult: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvMovies = view.findViewById(R.id.rv_movies)
        rvMovies.setHasFixedSize(true)

        dataViewModel = MainActivity.DataViewModel()
        dataViewModel.fetchMoviesList()

        searchViewMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                dataViewModel.searchMovieList(query)
                return false
            }
        })

        showRecyclerList()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dataViewModel.listDataMovie.observe(this, Observer<List<MovieResults>> {
            if (!isSearchResult) {
                listMovieAdapter.updateMovieList(it)
            }
        })

        dataViewModel.dataLoading.observe(this, Observer<Boolean> {
            if (it) {
                pbMovie.visibility = View.VISIBLE
                searchViewMovie.visibility = View.GONE
            } else {
                pbMovie.visibility = View.GONE
                searchViewMovie.visibility = View.VISIBLE
            }
        })

        dataViewModel.searchQueryMovie.observe(this, Observer<String> {
            if (it != "") {
                isSearchResult = true
                searchViewMovie.setQuery(it, false)
            }
        })

        dataViewModel.listDataSearchMovie.observe(this, Observer<List<MovieResults>> {
            if (isSearchResult) {
                listMovieAdapter.updateMovieList(it)
            }
        })
    }


    private fun showRecyclerList() {
        rvMovies.layoutManager = LinearLayoutManager(context)
        listMovieAdapter = ListMoviesAdapter(context!!.applicationContext, listMovieResult)
        rvMovies.adapter = listMovieAdapter
    }
}
