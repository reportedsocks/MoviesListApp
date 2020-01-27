package com.reportedsocks.restaurantlistapp.ui.main


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.reportedsocks.restaurantlistapp.R
import com.reportedsocks.restaurantlistapp.data.RestaurantDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_restaurant_profile.*

class RestaurantProfile : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = RestaurantProfile()
    }
    private lateinit var mMap: GoogleMap
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val model: MainViewModel = ViewModelProvider(this).get<MainViewModel>(MainViewModel::class.java)
        val bundle = getArguments()
        val id = bundle?.getString("id")?:"1"

        model.getRestaurant(id)?.observe(viewLifecycleOwner, Observer<RestaurantDetails>{ restaurant ->
            Picasso.get().load(restaurant.img).fit().centerCrop()
                .placeholder(R.drawable.ic_image_grey_200dp)
                .error(R.drawable.ic_image_grey_200dp)
                .into(profileRestImageView)

            nameTextView.text = restaurant.name
            if (restaurant.site.trim() == "" ){
                siteTextView.text = "No website"
            } else {
                siteTextView.text = restaurant.site
            }
            if (restaurant.phone.trim() == "" ){
                phoneTextView.text = "No phone"
            } else {
                phoneTextView.text = restaurant.phone
            }
            if (restaurant.description.trim() == "" ){
                descriptionTextView.text = "No description"
            } else {
                descriptionTextView.text = restaurant.description
            }

            val location = LatLng(restaurant.lat, restaurant.lon)
            mMap.addMarker(MarkerOptions().position(location).title(restaurant.name))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12f))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location))


            profileProgressBar.visibility = View.GONE
        })

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val mapSettings = mMap.uiSettings
        mapSettings.isZoomControlsEnabled = true
        mapSettings.isZoomGesturesEnabled = true
        mapSettings.isScrollGesturesEnabled = true
        mapSettings.isRotateGesturesEnabled = true
    }

}
