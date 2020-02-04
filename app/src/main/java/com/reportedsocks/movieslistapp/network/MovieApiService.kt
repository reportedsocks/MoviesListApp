package com.reportedsocks.movieslistapp.network

import com.reportedsocks.movieslistapp.data.MovieDetails
import com.reportedsocks.movieslistapp.data.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("/?apikey=867221d8")
    fun getMovies( @Query("s") s: String ): Observable<Result>
    @GET("/?apikey=867221d8")
    fun getMovieById( @Query("i") imdbID: String ): Observable<MovieDetails>
    @GET("/?apikey=867221d8")
    fun getMoviesByPage( @Query("s") imdbID: String, @Query("page") page: String ): Observable<Result>

}