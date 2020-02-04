package com.reportedsocks.movieslistapp.ui.moviedetails


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.reportedsocks.movieslistapp.MyApp
import com.reportedsocks.movieslistapp.R
import com.reportedsocks.movieslistapp.data.MovieDetails
import com.reportedsocks.movieslistapp.ui.MainViewModel
import com.reportedsocks.movieslistapp.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_details.*
import javax.inject.Inject


class MovieDetailsFragment : Fragment() {

    companion object {
        fun newInstance() =
            MovieDetailsFragment()
    }

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Injecting viewModelFactory
        (activity?.applicationContext as MyApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //set toolbar parameters
        toolbar = activity!!.findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { activity!!.onBackPressed() }
        toolbar.title = ""

        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val bundle = getArguments()
        val id = bundle?.getString("id")?:"1"

        viewModel.getMovie(id)?.observe(viewLifecycleOwner, Observer<MovieDetails>{ movie ->

            if(movie.Response == "True"){
                // update UI

                Picasso.get().load(movie.Poster).fit().centerInside()
                    .placeholder(R.drawable.ic_image_grey_200dp)
                    .error(R.drawable.ic_image_grey_200dp)
                    .into(posterDetailsImageView)

                toolbar.title = movie.Title

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

                movieRatingsListView.isEnabled = false
                movieRatingsListView.adapter = ReviewListAdapter(activity!!.applicationContext, movie.Ratings)
                Utils.setListViewHeightBasedOnChildren(movieRatingsListView)

                movieDetailsProgressBar.visibility = View.GONE

            } else {
                // show error and go back
                Toast.makeText(activity,
                    "Some error occurred during loading movie details",
                    Toast.LENGTH_LONG).show()
                activity!!.onBackPressed()
            }

        })

    }


}
