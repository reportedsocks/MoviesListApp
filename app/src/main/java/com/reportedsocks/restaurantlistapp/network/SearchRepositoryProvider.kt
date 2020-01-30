package com.reportedsocks.restaurantlistapp.network

object SearchRepositoryProvider {
    fun provideSearchRepository(): SearchRepository {
        return SearchRepository()
    }
}