package purwo.moviedbapps.view.ui.tvshow


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_tv_show.*
import purwo.moviedbapps.R

import purwo.moviedbapps.view.adapter.ListTvShowsAdapter
import purwo.moviedbapps.model.tv.TVResults
import purwo.moviedbapps.view.ui.MainActivity
import purwo.moviedbapps.view.ui.viewmodel.DataViewModel

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : Fragment() {
    private lateinit var rvTvShow : RecyclerView
    private var listTVResult : List<TVResults> = listOf()
    private lateinit var dataViewModel: DataViewModel
    private lateinit var listTvShowAdapter: ListTvShowsAdapter
    var isSearchResult : Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvTvShow = view.findViewById(R.id.rv_tvshows)
        rvTvShow.setHasFixedSize(true)

        dataViewModel = MainActivity.DataViewModel()
        dataViewModel.fetchTVList()

        searchViewTV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                dataViewModel.searchTVList(query)
                return false
            }
        })

        showRecyclerList()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dataViewModel.listDataTV.observe(this, Observer<List<TVResults>>{
            if(!isSearchResult) {
                listTvShowAdapter.updateTvList(it)
            }
        })

        dataViewModel.dataLoading.observe(this, Observer<Boolean>{
            if(it){
                pbLoading.visibility = View.VISIBLE
            }else{
                pbLoading.visibility = View.GONE
            }
        })

        dataViewModel.searchQueryTV.observe(this, Observer<String>{
            if(it != ""){
                isSearchResult = true
                searchViewTV.setQuery(it, false)
            }
        })

        dataViewModel.listDataSearchTV.observe(this, Observer<List<TVResults>>{
            if(isSearchResult) {
                listTvShowAdapter.updateTvList(it)
            }
        })
    }

    private fun showRecyclerList(){
        rvTvShow.layoutManager = LinearLayoutManager(context)
        listTvShowAdapter = ListTvShowsAdapter(context!!.applicationContext, listTVResult)
        rvTvShow.adapter = listTvShowAdapter
    }
}
