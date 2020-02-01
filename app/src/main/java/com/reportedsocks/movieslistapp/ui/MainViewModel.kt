package com.reportedsocks.movieslistapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reportedsocks.movieslistapp.data.MovieDetails
import com.reportedsocks.movieslistapp.data.MoviePreview
import com.reportedsocks.movieslistapp.network.SearchRepositoryProvider
import com.reportedsocks.movieslistapp.ui.movieslist.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainViewModel: ViewModel() {
    private val repository = SearchRepositoryProvider.provideSearchRepository()

    //observable event for RecyclerView item click
    internal val selectItemEvent = SingleLiveEvent<MoviePreview?>()

    //observable vars for fragments
    private var movies: MutableLiveData<List<MoviePreview>>? = null
    private var movie: MutableLiveData<MovieDetails>? = null
    private var error: MutableLiveData<String>? = null

    //parameters of last search
    private var search: String? = null
    private var page: Int = 2

    // scroll position of RecyclerView
    var recyclerViewScroll: List<Int?> = listOf(null, null)

    fun getMovies(): LiveData<List<MoviePreview>>? {
        if (movies == null) {
            movies = MutableLiveData()
        }
        return movies
    }

    fun getError(): LiveData<String>? {
        if (error == null) {
            error = MutableLiveData()
        }
        return error
    }

    fun discardError(){
        error = null
    }

    fun getMovie(id: String): LiveData<MovieDetails>? {
        if (movie == null) {
            movie = MutableLiveData()
            loadMovie(id)
        }
        return movie
    }

    fun loadMovies( s: String ) {
        repository.getMovies( s )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                if(result.Response == "True"){
                    // update movies list
                    movies?.postValue(result.Search)
                    //save search parameters
                    search = s
                    page = 2
                } else {
                    // update error
                    error?.postValue(result.Error)
                }
            }, { error ->
                error.printStackTrace()
            })
    }

    private fun loadMovie(id: String) {

        repository.getMovieById( id )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({  result ->
                movie?.postValue(result)
            }, { error ->
                error.printStackTrace()
            })
    }

    fun loadNextPage(){
        // save current movies list
        val prevList: MutableList<MoviePreview> = movies!!.value!!.toMutableList()

        repository.getMoviesById( search?:"default", page.toString() )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->

                // add new movies to the prevList
                result.Search.forEach {
                    prevList.add(prevList.size, it)
                }
                // update movies with prevList + new items
                movies?.postValue(prevList.toList())
            }, { error ->
                error.printStackTrace()
            })
        // increase page value for next request
        page++
    }

}


