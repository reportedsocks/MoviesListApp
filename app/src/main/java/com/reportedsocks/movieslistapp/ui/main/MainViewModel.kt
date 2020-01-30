package com.reportedsocks.movieslistapp.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.reportedsocks.movieslistapp.data.MovieDetails
import com.reportedsocks.movieslistapp.data.MoviePreview
import com.reportedsocks.movieslistapp.network.SearchRepositoryProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SearchRepositoryProvider.provideSearchRepository()
    internal val selectItemEvent = SingleLiveEvent<MoviePreview?>()
    private var movies: MutableLiveData<List<MoviePreview>>? = null
    private var movie: MutableLiveData<MovieDetails>? = null

    fun getMovies(): LiveData<List<MoviePreview>>? {
        if (movies == null) {
            movies = MutableLiveData()
            loadMovies()
        }
        return movies
    }

    fun getMovie(id: String): LiveData<MovieDetails>? {
        if (movie == null) {
            movie = MutableLiveData()
            loadMovie(id)
        }
        return movie
    }

    private fun loadMovies() {

        repository.getMovies()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({
                    result ->
                Log.d("MyLogs", "List of movies: ${result} ")
                movies?.postValue(result.Search)
            }, { error ->
                error.printStackTrace()
            })
    }

    private fun loadMovie(id: String) {

        repository.getMovieById( id )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                    result ->
                Log.d("MyLogs", "Movie Details: ${result} ")
                movie?.postValue(result)
            }, { error ->
                error.printStackTrace()
            })
    }

}


