package com.reportedsocks.movieslistapp.network

object SearchRepositoryProvider {
    fun provideSearchRepository(): SearchRepository {
        return SearchRepository()
    }
}