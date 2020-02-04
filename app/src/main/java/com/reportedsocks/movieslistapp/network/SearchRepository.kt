package com.reportedsocks.movieslistapp.network

import com.reportedsocks.movieslistapp.data.MovieDetails
import com.reportedsocks.movieslistapp.data.Result
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(val movieApiService: MovieApiService){

    fun getMovies( s: String ): Observable<Result> {
        return movieApiService.getMovies( s )
    }
    fun getMovieById( imdbID: String ): Observable<MovieDetails> {
        return movieApiService.getMovieById( imdbID )
    }
    fun getMoviesById( s: String , page: String): Observable<Result> {
        return movieApiService.getMoviesByPage( s, page )
    }
}