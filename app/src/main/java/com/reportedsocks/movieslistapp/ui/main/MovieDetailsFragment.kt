package com.reportedsocks.movieslistapp.ui.main


import android.os.Bundle
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
import com.reportedsocks.movieslistapp.R
import com.reportedsocks.movieslistapp.data.MovieDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_details.*

class MovieDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = MovieDetailsFragment()
    }
    private lateinit var mMap: GoogleMap
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val model: MainViewModel = ViewModelProvider(this).get<MainViewModel>(MainViewModel::class.java)
        val bundle = getArguments()
        val id = bundle?.getString("id")?:"1"

        model.getMovie(id)?.observe(viewLifecycleOwner, Observer<MovieDetails>{ movie ->
            Picasso.get().load(movie.Poster).fit().centerInside()
                .placeholder(R.drawable.ic_image_grey_200dp)
                .error(R.drawable.ic_image_grey_200dp)
                .into(posterDetailsImageView)

            titleDetailsTextView.text = movie.Title
            yearDetailsTextView.text = movie.Year
            ratingTextView.text = movie.Rated
            runtimeTextView.text = movie.Runtime
            genreTextView.text = movie.Genre
            directorTextView.text = movie.Director
            writerTextView.text = movie.Writer
            actorsTextView.text = movie.Actors
            languageTextView.text = movie.Language
            descriptionTextView.text = movie.Plot




            movieDetailsProgressBar.visibility = View.GONE
        })

    }



}
