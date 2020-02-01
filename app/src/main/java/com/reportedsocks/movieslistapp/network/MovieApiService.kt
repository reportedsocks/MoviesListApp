package com.reportedsocks.movieslistapp.network

import com.reportedsocks.movieslistapp.data.MovieDetails
import com.reportedsocks.movieslistapp.data.Result
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("/?apikey=867221d8")
    fun getMovies( @Query("s") s: String ): Observable<Result>
    @GET("/?apikey=867221d8")
    fun getMovieById( @Query("i") imdbID: String ): Observable<MovieDetails>
    @GET("/?apikey=867221d8")
    fun getMoviesByPage( @Query("s") imdbID: String, @Query("page") page: String ): Observable<Result>


    //Companion object to create the MovieApiService
    companion object Factory {
        fun create(): MovieApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://www.omdbapi.com")
                .build()

            return retrofit.create(MovieApiService::class.java)
        }
    }
}