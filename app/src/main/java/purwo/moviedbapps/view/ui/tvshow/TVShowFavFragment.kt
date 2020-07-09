package purwo.moviedbapps.view.ui.tvshow

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

class TVShowFavFragment : Fragment() {
    private lateinit var rvTVs : RecyclerView
    private var listTVResult : List<FavouriteItem> = listOf()
    private lateinit var listTVAdapter: ListFavouriteAdapter
    private lateinit var favViewModel: FavouriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow_fav, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvTVs = view.findViewById(R.id.rv_tvs_fav)
        rvTVs.setHasFixedSize(true)

        favViewModel = MainActivity.FavouriteViewModel()

        showRecyclerList()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        favViewModel.listTV.observe(this, Observer<List<FavouriteItem>>{
            listTVAdapter.updateFavouriteList(it)
        })
    }

    override fun onResume() {
        super.onResume()
        favViewModel.getTvFavList()
    }

    private fun showRecyclerList(){
        rvTVs.layoutManager = LinearLayoutManager(context)
        listTVAdapter = ListFavouriteAdapter(context!!.applicationContext, listTVResult)
        rvTVs.adapter = listTVAdapter
    }
}

