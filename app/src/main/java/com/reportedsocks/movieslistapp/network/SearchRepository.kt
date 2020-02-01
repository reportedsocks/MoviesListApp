package com.reportedsocks.movieslistapp.network

import com.reportedsocks.movieslistapp.data.MovieDetails
import com.reportedsocks.movieslistapp.data.Result
import io.reactivex.Observable

class SearchRepository {

    fun getMovies( s: String ): Observable<Result> {
        return MovieApiService.create().getMovies( s )
    }
    fun getMovieById( imdbID: String ): Observable<MovieDetails> {
        return MovieApiService.create().getMovieById( imdbID )
    }
    fun getMoviesById( s: String , page: String): Observable<Result> {
        return MovieApiService.create().getMoviesByPage( s, page )
    }
}