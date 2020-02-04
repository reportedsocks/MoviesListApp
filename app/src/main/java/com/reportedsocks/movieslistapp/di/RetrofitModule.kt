package com.reportedsocks.movieslistapp.di

import com.reportedsocks.movieslistapp.network.MovieApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {
    @Singleton
    @Provides
    fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://www.omdbapi.com")
            .build()
    }
    @Singleton
    @Provides
    fun getMovieApiService(retrofit: Retrofit): MovieApiService{
        return retrofit.create(MovieApiService::class.java)
    }

}