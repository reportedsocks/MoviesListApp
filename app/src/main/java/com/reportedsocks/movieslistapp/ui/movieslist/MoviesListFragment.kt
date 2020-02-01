package com.reportedsocks.movieslistapp.ui.movieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reportedsocks.movieslistapp.R
import com.reportedsocks.movieslistapp.data.MoviePreview
import com.reportedsocks.movieslistapp.ui.MainViewModel
import com.reportedsocks.movieslistapp.ui.ViewModelFactory
import com.reportedsocks.movieslistapp.ui.moviedetails.MovieDetailsFragment
import kotlinx.android.synthetic.main.fragment_movies_list.*


class MoviesListFragment : Fragment() {

    companion object {
        fun newInstance() =
            MoviesListFragment()
    }

    private lateinit var adapter: MoviesAdapter
    // activates loading next page at reaching bottom of RecyclerView,
    // when first request was completed successfully
    private var searchCompleted = false
    // if activity is restarted it will save the previous scroll position
    private var hasSavedScroll = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progressBar.visibility = View.GONE

        val model: MainViewModel = ViewModelProvider(this, ViewModelFactory()).get(MainViewModel::class.java)
        val linearLayoutManager = LinearLayoutManager(activity!!.applicationContext)
        list.layoutManager = linearLayoutManager

        //get previously saved scroll
        var savedIndex: Int? = 0
        var savedTop: Int? = 0
        if(model.recyclerViewScroll[0] != null ){
            savedIndex = model.recyclerViewScroll[0]
            savedTop = model.recyclerViewScroll[1]
            hasSavedScroll = true
        }

        //observes successful request result
        model.getMovies()?.observe(viewLifecycleOwner, Observer<List<MoviePreview>>{ movies ->
            if(movies != null){

                    // don't save scroll at 0 if activity was restarted
                    if(!hasSavedScroll){
                        model.recyclerViewScroll = getCurrentScroll(linearLayoutManager)
                    }

                    //update adapter with new values
                    adapter = MoviesAdapter(model)
                    adapter.replaceItems(movies)
                    list.adapter = adapter

                    // set scroll after recycler update
                    if(hasSavedScroll){
                        linearLayoutManager.scrollToPositionWithOffset(savedIndex!!, savedTop!!)
                        hasSavedScroll = false
                    } else {
                        if(model.recyclerViewScroll[0] != null ){
                            linearLayoutManager.scrollToPositionWithOffset(
                                model.recyclerViewScroll[0]!!,
                                model.recyclerViewScroll[1]?:0)
                        }
                    }
                    searchCompleted = true

                progressBar.visibility = View.GONE
            } else {
                progressBar.visibility = View.VISIBLE
            }

        })

        //observes error request result
        model.getError()?.observe(viewLifecycleOwner, Observer<String>{
            if(it != null && it.isNotEmpty()){
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
                model.discardError()
            }
        })

        // observes onClick event on RecyclerView items
        model.selectItemEvent.observe(viewLifecycleOwner, Observer{
            if (it != null) {

                // save scroll
                model.recyclerViewScroll = getCurrentScroll(linearLayoutManager)

                // start new fragment and put movie id in bundle
                val bundle = Bundle(0)
                bundle.putString("id", it.imdbID)
                val fragment = MovieDetailsFragment.newInstance()
                fragment.setArguments(bundle)
                val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.container, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        })

        // fires loading next page when scrolled to the bottom of RecyclerView
        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if(searchCompleted){
                        model.loadNextPage()
                        progressBar.visibility = View.VISIBLE
                    }

                }
            }
        })
    }

    private fun getCurrentScroll(linearLayoutManager: LinearLayoutManager):List<Int?>{
        val index: Int = linearLayoutManager.findFirstVisibleItemPosition()
        val v: View? = linearLayoutManager.getChildAt(0)
        val top =
            if (v == null) 0 else v.top - linearLayoutManager.paddingTop
        return listOf(index, top)
    }

}


