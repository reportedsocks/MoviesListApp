package com.reportedsocks.restaurantlistapp.network

import com.reportedsocks.restaurantlistapp.data.RestaurantDetails
import com.reportedsocks.restaurantlistapp.data.RestaurantPreview
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantApiService {

    @GET("test_task/test.php")
    fun getRestaurants(): Observable<List<RestaurantPreview>>
    @GET("test_task/test.php")
    fun getRestaurantById( @Query("id") id:String ): Observable<List<RestaurantDetails>>

    /**
     * Companion object to create the GithubApiService
     */
    companion object Factory {
        fun create(): RestaurantApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://megakohz.bget.ru/")
                .build()

            return retrofit.create(RestaurantApiService::class.java)
        }
    }
}