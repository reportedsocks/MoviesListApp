package com.reportedsocks.movieslistapp.network

import com.reportedsocks.movieslistapp.data.MovieDetails
import com.reportedsocks.movieslistapp.data.MoviePreview
import com.reportedsocks.movieslistapp.data.Result
import io.reactivex.Observable

class SearchRepository {

    fun getMovies(): Observable<Result> {
        return MovieApiService.create().getMovies("batman")
    }
    fun getMovieById( imdbID: String ): Observable<MovieDetails> {
        return MovieApiService.create().getMovieById( imdbID )
    }
}