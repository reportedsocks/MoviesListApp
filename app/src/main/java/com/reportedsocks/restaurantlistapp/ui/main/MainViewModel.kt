package com.reportedsocks.restaurantlistapp.ui.main

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.reportedsocks.restaurantlistapp.data.MySingleton
import com.reportedsocks.restaurantlistapp.data.RestaurantDetails
import com.reportedsocks.restaurantlistapp.data.RestaurantPreview
import org.json.JSONArray
import java.lang.Exception


class MainViewModel(application: Application) : AndroidViewModel(application) {
    internal val selectItemEvent = SingleLiveEvent<RestaurantPreview?>()
    private var restaurants: MutableLiveData<List<RestaurantPreview>>? = null
    private var restaurant: MutableLiveData<RestaurantDetails>? = null

    fun getRestaurants(): LiveData<List<RestaurantPreview>>? {
        if (restaurants == null) {
            restaurants = MutableLiveData()
            loadRestaurants()
        }
        return restaurants
    }

    fun getRestaurant(id: String): LiveData<RestaurantDetails>? {
        if (restaurant == null) {
            restaurant = MutableLiveData()
            loadRestaurant(id)
        }
        return restaurant
    }

    private fun loadRestaurants() {
        AsyncRestaurantsRequest().execute(mutableListOf(
            getApplication() as Application,
            restaurants as MutableLiveData<List<RestaurantPreview>>
        ))
    }

    private fun loadRestaurant(id: String) {
        AsyncRestaurantRequest().execute(mutableListOf(id,
            getApplication() as Application,
            restaurant as MutableLiveData<RestaurantDetails> ))
    }

    // Would move the following to other class but for the test task it's ok I think
    private class AsyncRestaurantRequest : AsyncTask<List<Any>, Void,Unit>(){
        override fun doInBackground(vararg lists: List<Any>) {
            val id: String = lists[0][0] as String
            val application: Application = lists[0][1] as Application
            val restaurant: MutableLiveData<RestaurantDetails> =
                    lists[0][2] as MutableLiveData<RestaurantDetails>
            val listUrl = "http://megakohz.bget.ru/test_task/test.php?id=" + id
            val request = JsonArrayRequest(
                Request.Method.GET, listUrl, null,
                Response.Listener<JSONArray> { response ->
                    try {
                        val item = response.getJSONObject(0)
                        val restaurantProfile = RestaurantDetails( id,
                            item.getString("name"),
                            "http://megakohz.bget.ru/test_task/" + item.getString("img"),
                            item.getString("description"),
                            item.getDouble("lat"),
                            item.getDouble("lon"),
                            item.getString("www"),
                            item.getString("phone"))
                        restaurant.postValue(restaurantProfile)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    Log.d("MyLogs", error.message?:"Couldn't get the error message")
                    Toast.makeText(application, "Error", Toast.LENGTH_SHORT).show()
                })
            MySingleton.getInstance(application).addToRequestQueue(request)
        }
    }

    private class AsyncRestaurantsRequest : AsyncTask<List<Any>, Void,Unit>(){
        override fun doInBackground(vararg lists: List<Any>) {
            val application: Application = lists[0][0] as Application
            val restaurants: MutableLiveData<List<RestaurantPreview>> =
                    lists[0][1] as MutableLiveData<List<RestaurantPreview>>
            val restaurantList = mutableListOf<RestaurantPreview>()
            val listUrl = "http://megakohz.bget.ru/test_task/test.php"
            val request = JsonArrayRequest(
                Request.Method.GET, listUrl, null,
                Response.Listener<JSONArray> { response ->
                    try {
                        for (i in 0 until response.length()) {
                            val item = response.getJSONObject(i)
                            restaurantList.add(
                                RestaurantPreview(
                                    item.getString("id"),
                                    item.getString("name"),
                                    "http://megakohz.bget.ru/test_task/" + item.getString("img")
                                )
                            )
                        }
                        restaurants.postValue(restaurantList)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    Log.d("MyLogs", error.message?:"Couldn't get the error message")
                    Toast.makeText(application, "Error", Toast.LENGTH_SHORT).show()
                })
            MySingleton.getInstance(application).addToRequestQueue(request)
        }
    }

}


