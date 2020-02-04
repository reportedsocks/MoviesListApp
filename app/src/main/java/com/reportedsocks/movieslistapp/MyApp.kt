package com.reportedsocks.movieslistapp

import android.app.Application
import com.reportedsocks.movieslistapp.di.RetrofitModule
import com.reportedsocks.movieslistapp.di.ViewModelModule
import com.reportedsocks.movieslistapp.network.MovieApiService
import com.reportedsocks.movieslistapp.ui.moviedetails.MovieDetailsFragment
import com.reportedsocks.movieslistapp.ui.movieslist.MoviesListFragment
import com.reportedsocks.movieslistapp.ui.movieslist.SearchFieldFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [RetrofitModule::class, ViewModelModule::class] )
interface ApplicationComponent{
    fun inject(fragment: MoviesListFragment)
    fun inject(fragment: SearchFieldFragment)
    fun inject(fragment: MovieDetailsFragment)
    fun getMovieApiService(): MovieApiService
}

class MyApp: Application() {

    val appComponent = DaggerApplicationComponent.create()

}