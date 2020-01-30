package com.reportedsocks.restaurantlistapp.network

import com.reportedsocks.restaurantlistapp.data.RestaurantDetails
import com.reportedsocks.restaurantlistapp.data.RestaurantPreview
import io.reactivex.Observable

class SearchRepository {
    fun getRestaurants(): Observable<List<RestaurantPreview>> {
        return RestaurantApiService.create().getRestaurants()
    }
    fun getRestaurantById( id: String ): Observable<List<RestaurantDetails>> {
        return RestaurantApiService.create().getRestaurantById( id )
    }
}